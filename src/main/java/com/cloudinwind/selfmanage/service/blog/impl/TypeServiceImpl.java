package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.Type;
import com.cloudinwind.selfmanage.mapper.blog.TypeDao;
import com.cloudinwind.selfmanage.service.blog.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Type)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 11:40:20
 */
@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

    @Resource
    TypeDao typeDao;

    @Override
    public List<Type> getUserBlogType(Long userId) {
        return typeDao.getUserBlogType(userId);
    }

    @Override
    public List<Type> getUserAllType(Long userId) {
        return typeDao.getUserAllType(userId);
    }

    @Override
    public Type getTypeById(Long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public Type getUserTypeByName(String name, Long userId) {
        return typeDao.getUserTypeByName(name, userId);
    }


    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeDao.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }
}

