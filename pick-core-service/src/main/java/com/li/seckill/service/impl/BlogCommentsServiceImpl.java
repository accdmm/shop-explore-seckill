package com.li.seckill.service.impl;

import com.li.seckill.entity.BlogComments;
import com.li.seckill.mapper.BlogCommentsMapper;
import com.li.seckill.service.IBlogCommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 博客评论接口实现
 * @author: 李梦豪
 **/
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
