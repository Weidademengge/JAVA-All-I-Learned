package com.ebanma.cloud.demo.domain.service;

import com.ebanma.cloud.demo.data.dao.UserDao;
import com.ebanma.cloud.demo.data.model.UserDO;
import com.ebanma.cloud.demo.domain.entity.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private IdGenerator idGenerator;

    @Value("${userService.canModify}")
    private Boolean canModify;

    public Long saveUser(UserVO userSave) {
        Long userId = userDao.getIdByName(userSave.getName());
        // 不存在则创建
        if (Objects.isNull(userId)) {
            userId = idGenerator.next();
            UserDO userCreate = new UserDO();
            userCreate.setId(userId);
            userCreate.setName(userSave.getName());
            userCreate.setDescription(userSave.getDescription());
            userDao.create(userCreate);
        }
        // 已存在可修改
        else if (Boolean.TRUE.equals(canModify)) {
            UserDO userModify = new UserDO();
            userModify.setId(userId);
            userModify.setName(userSave.getName());
            userModify.setDescription(userSave.getDescription());
            userDao.modify(userModify);
        }
        // 已存在禁止修改
        else {
            throw  new UnsupportedOperationException("不支持修改");
        }
        // 返回用户标识
        return userId;
    }
}