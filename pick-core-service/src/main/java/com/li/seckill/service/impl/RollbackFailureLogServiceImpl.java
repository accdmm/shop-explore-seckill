package com.li.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.li.seckill.entity.RollbackFailureLog;
import com.li.seckill.mapper.RollbackFailureLogMapper;
import com.li.seckill.service.IRollbackFailureLogService;
import org.springframework.stereotype.Service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 回滚失败日志 接口实现
 * @author: 李梦豪
 **/
@Service
public class RollbackFailureLogServiceImpl extends ServiceImpl<RollbackFailureLogMapper, RollbackFailureLog>
        implements IRollbackFailureLogService {
}