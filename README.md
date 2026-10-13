# shop-explore-seckill

> 探店场景的优惠券秒杀平台。围绕「高并发抢购」这一核心场景，重点解决**库存超卖、缓存穿透/击穿、消息可靠性与 Redis 与数据库的一致性**问题，并补充订阅提醒、库存回流等原因项目缺失的能力。

技术栈：`Spring Boot 3` · `Redis + Lua` · `Redisson` · `Kafka` · `ShardingSphere` · `MyBatis-Plus` · `Caffeine` · `MySQL` · `Vue 3`

---

## 一、业务场景

平台面向本地商户，提供商户浏览、优惠券发放与抢购、达人探店、好友关注、签到统计等社交电商能力。

其中**优惠券秒杀**是技术上的核心：瞬时流量高、库存必须精确、失败路径多。项目在这条链路上下沉了完整的一致性保障机制，而不是停留在"能扣减成功"的 Demo 层面。

---

## 二、核心能力

### 1. 秒杀链路：资格判定与库存扣减原子化

- **四重校验 + 原子扣减**：活动时间窗、券状态、库存充足性、一人一单，四项校验与 `incrby -1` 扣减在**同一个 Lua 脚本**内完成，避免"检查通过、扣减前被抢占"的 TOCTOU 超卖窗口。
- **服务端时间校验**：脚本内用 `redis.call('TIME')` 取服务端时间，规避客户端时钟篡改与漂移。
- **扣减流水**：扣减成功时同步写入 `trace` 流水（含扣减前 / 扣减量 / 扣减后），作为后续对账与精确回滚的依据。
- **异步落库**：扣减成功后投递 Kafka 并立即返回订单号，数据库写入完全移出同步链路 —— 同步 RT 不随订单量增长。

### 2. 超卖防护：分层职责

| 层次 | 职责 | 说明 |
|---|---|---|
| Redis（性能层） | 快速失败，拦截绝大多数无效请求 | 预扣，**允许不精确** |
| 数据库（正确性层） | 最终裁决 | `set stock = stock - 1 where stock > 0`，判断与扣减在同一条语句内完成 |

即使 Redis 层出现任何偏差，数据库的条件更新也不会让库存扣成负数。去掉该条件才会真正超卖。

### 3. 一致性闭环：三层收敛

异步落库必然存在"Redis 已扣、数据库未落"的窗口，项目用三层机制收敛：

- **实时回滚**：生产失败、消息超时、消费失败三个触发点自动回滚 —— 失效库存 key 触发重载 + 按规则移除购买资格。回滚采用**删 key 而非加减库存**，保证幂等（未 ack 的消息必然重投，回滚会被执行多次）；重试带**指数退避 + 随机抖动**，避免批量失败齐步重试形成重试风暴。
- **定时对账**：**双向核对** —— Redis 有扣减流水而数据库无对账日志，判定"扣了没落库"；数据库有日志而 Redis 无流水，则回填流水并触发重载。两端比对均带**宽限期**，避开在途消息，防止误判。
- **人工兜底**：回滚重试耗尽后落库 + 告警；对账发现的异常订单标记为 `ABNORMAL` 交人工处理，不自动修改。

### 4. 缓存体系：穿透与击穿分别处理

- **防穿透**：布隆过滤器为主（判定不存在即拒绝），空值缓存兜误判（短 TTL，防止恶意 key 刷爆内存）。
- **防击穿**：本地缓存（Caffeine）+ Redis 双层；回源走**分布式锁 + 双重检查**的单飞逻辑，保证同一 key 只有一个线程查库。
- **一致性**：回源读持**读锁**、缓存更新与失效持**写锁**，防止"删缓存后被并发读回填旧值"。
- **多实例失效**：缓存失效经 Kafka 广播（随机 groupId），在写锁下同时清理本地缓存与 Redis 相关 key，失败进 DLQ。

### 5. 全链路流控

- **限流**：基于 Redis + Lua 实现**滑动窗口**与**令牌桶**两套算法（配置可切换），支持 IP + 用户双维度、按活动隔离、白名单与违规封禁。
- **前置资格令牌**：抢购前先申请一次性令牌（短 TTL、Lua 原子"校验并消费"），拦截脚本刷单与提前囤积令牌。

### 6. 幂等与分布式锁

- **幂等注解**：本地锁快速失败 → Redisson 公平锁跨实例互斥 → 执行成功后写入防重标记，应用于消息消费等场景。
- **注解式分布式锁**：支持可重入锁、公平锁、读锁、写锁四种类型，可注解声明或编程式调用，支持自定义获取超时策略。

### 7. 数据层

- **分库分表**：用户、券、订单、对账日志等按维度分片（`voucher_id` / `user_id` / `order_id`）。
- **订单路由表**：订单表按 `user_id` 分库、`voucher_id` 分表后，按订单号查询无法路由，因此引入按 `order_id` 分片的**路由表**并配短 TTL 缓存。
- **全局 ID**：雪花算法生成，`workerId` / `datacenterId` 由 Redis 统一分配，避免多实例配置冲突。

### 8. 订阅与库存回流

- **订阅候补**：库存不足时用户可订阅提醒，用 ZSet 按订阅时间排队；重复订阅不刷新分数，防止插队。
- **回流自动补发**：订单取消释放库存后，从订阅队列中选取最早未购用户，校验人群规则后**复用秒杀扣减脚本**自动补发（因此天然具备原子扣减、一人一单、时间窗校验三重保障），并触发带去重窗口的通知。

---

## 三、模块结构

| 模块 | 职责 |
|---|---|
| `hmdp-common` | 常量、枚举、异常、ThreadLocal 工具 |
| `hmdp-sharding` | 分库分表依赖封装 |
| `hmdp-core-service` | 核心业务：Controller / Service / Lua / Kafka / 初始化 |
| `hmdp-redisson-framework` | 分布式锁（注解式、四种锁类型）、布隆过滤器、防重执行 |
| `hmdp-redis-tool-framework` | Redis 客户端封装、限流（滑动窗口 / 令牌桶）、前置令牌 |
| `hmdp-id-generator-framework` | 雪花算法 + Redis 分配 workerId |
| `hmdp-mq-framework` | Kafka 生产 / 消费抽象基类、消息封装、延迟队列 |
| `hmdp-parameter` | DTO / VO 契约层 |
| `hmdp-vue3` | 前端（Vue 3 + Vite + Element Plus + Pinia） |

各框架模块通过 Spring Boot 自动装配（`META-INF/spring/...AutoConfiguration.imports`）接入，业务服务按需依赖。

---

## 四、快速开始

### 1. 环境要求

| 组件 | 版本 / 地址 |
|---|---|
| JDK | 17 |
| MySQL | 8.x，需建两个库：`hmdp_0`、`hmdp_1` |
| Redis | `127.0.0.1:6379`（database 0） |
| Kafka | `localhost:9092` |
| Node.js | 前端构建需要 |

### 2. 初始化数据库

依次执行 `sql/` 目录下的脚本：

```bash
sql/1_create_database.sql   # 建库
sql/hmdp_0.sql              # 分片库 0
sql/hmdp_1.sql              # 分片库 1
```

数据库连接信息见 `hmdp-core-service/src/main/resources/shardingsphere.yaml`。

### 3. 启动后端

```bash
mvn clean install
# 运行 hmdp-core-service 模块的启动类 HmDianPingApplication
```

服务端口 `8085`。Redis、Kafka 连接信息见 `hmdp-core-service/src/main/resources/application.yml`。

> 首次启动会自动执行：布隆过滤器初始化 → 活动时间维护 → 缓存清理（见 `init/` 目录下的初始化器）。

### 4. 启动前端

```bash
cd hmdp-vue3
npm install
npm run dev
```

前端开发服务器已将 `/api` 代理至 `http://localhost:8085`。

---

## 五、核心技术点索引

| 主题 | 代码位置 |
|---|---|
| 秒杀资格判定与原子扣减 | `hmdp-core-service/src/main/resources/lua/seckillVoucher.lua` |
| 回滚脚本 | `hmdp-core-service/src/main/resources/lua/seckillVoucherRollBack.lua` |
| 秒杀主流程 / 取消 / 补发 | `.../service/impl/VoucherOrderServiceImpl.java` |
| 多级缓存与单飞回源 | `.../service/impl/SeckillVoucherServiceImpl.java` |
| 本地缓存（Caffeine） | `.../cache/SeckillVoucherLocalCache.java` |
| 回滚重试与告警 | `.../kafka/redis/RedisVoucherData.java` |
| 消息消费（超时丢弃 / 回滚 / 旁路） | `.../kafka/consumer/SeckillVoucherConsumer.java` |
| 定时对账任务 | `.../service/impl/ReconciliationTaskServiceImpl.java` |
| 限流算法（滑动窗口 / 令牌桶） | `hmdp-redis-rate-limit-framework/src/main/resources/lua/*.lua` |
| 防重执行切面 | `hmdp-repeat-execute-limit-framework/.../RepeatExecuteLimitAspect.java` |
| 分库分表配置 | `hmdp-core-service/src/main/resources/shardingsphere.yaml` |
| 消费基类（ack 与重投） | `hmdp-mq-consumer-framework/.../AbstractConsumerHandler.java` |

---

## 六、License

[Apache License 2.0](LICENSE)
