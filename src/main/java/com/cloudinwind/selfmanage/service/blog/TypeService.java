package com.cloudinwind.selfmanage.service.blog;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.blog.Type;

import java.util.List;

/**
 * (Type)表服务接口
 *
 * @author makejava
 * @since 2022-04-24 11:40:20
 */
public interface TypeService extends IService<Type> {

    // 获取该用户下的分类(包含该分类下的博客)
    List<Type> getUserBlogType(Long userId);

    // 获取该用户下的所有分类(不包含该分类下的博客)
    List<Type> getUserAllType(Long userId);

    Type getTypeById(Long id);

    Type getUserTypeByName(String name, Long userId);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(Long id);

    // 查询有无该分类(该分类不一定属于该用户)
    Type getTypeByName(String name);
}

