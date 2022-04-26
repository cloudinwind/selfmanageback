package com.cloudinwind.selfmanage.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.blog.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Type)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-24 11:40:20
 */
public interface TypeDao extends BaseMapper<Type> {

    // 获取该用户下的分类(包含该分类下的博客)
    List<Type> getUserBlogType(@Param("userId") Long userId);


    // 获取该用户下的所有分类(不包含该分类下的博客)
    List<Type> getUserAllType(@Param("userId") Long userId);

    Type getTypeById(Long id);

    // 根据类型名称获取该用户下对应的类型
    Type getUserTypeByName(@Param("name") String name, @Param("userId")Long userId);

    int saveType(Type type);

    int updateType(Type type);

    int deleteType(Long id);

    // 查询有无该分类(该分类不一定属于该用户)
    Type getTypeByName(String name);

}

