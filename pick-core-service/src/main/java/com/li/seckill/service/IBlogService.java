package com.li.seckill.service;

import com.li.seckill.dto.Result;
import com.li.seckill.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 博客接口
 * @author: 李梦豪
 **/
public interface IBlogService extends IService<Blog> {

    Result queryHotBlog(Integer current);

    Result queryBlogById(Long id);

    Result likeBlog(Long id);

    Result queryBlogLikes(Long id);

    Result saveBlog(Blog blog);

    Result queryBlogOfFollow(Long max, Integer offset);

}
