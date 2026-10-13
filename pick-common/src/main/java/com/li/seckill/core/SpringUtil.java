package com.li.seckill.core;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import static com.li.seckill.constant.Constant.DEFAULT_PREFIX_DISTINCTION_NAME;
import static com.li.seckill.constant.Constant.PREFIX_DISTINCTION_NAME;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: spring工具
 * @author: 李梦豪
 **/
public class SpringUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    private static ConfigurableApplicationContext configurableApplicationContext;
    
    
    public static String getPrefixDistinctionName(){
        return configurableApplicationContext.getEnvironment().getProperty(PREFIX_DISTINCTION_NAME,
                DEFAULT_PREFIX_DISTINCTION_NAME);
    }
    
    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        configurableApplicationContext = applicationContext;
    }
}
