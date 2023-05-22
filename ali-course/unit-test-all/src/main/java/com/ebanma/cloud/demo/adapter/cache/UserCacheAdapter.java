package com.ebanma.cloud.demo.adapter.cache;

import com.ebanma.cloud.demo.adapter.cache.model.UserCacheDTO;
import com.ebanma.cloud.demo.adapter.integration.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserCacheAdapter {

    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public UserCacheAdapter(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public UserCacheDTO getUserByName(String name) {
        return (UserCacheDTO) redisTemplate.opsForValue().get(name);
    }

    public UserCacheDTO getUserById(Long id) {
        return (UserCacheDTO) redisTemplate.opsForValue().get(id.toString());
    }

    public void cacheUser(UserDTO userDTO) {
        UserCacheDTO userCacheDTO = new UserCacheDTO();
        userCacheDTO.setId(userDTO.getId());
        userCacheDTO.setName(userDTO.getName());
        redisTemplate.opsForValue().set(userDTO.getId().toString(), userCacheDTO);
    }
}