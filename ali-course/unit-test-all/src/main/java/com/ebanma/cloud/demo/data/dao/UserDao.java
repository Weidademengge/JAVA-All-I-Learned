package com.ebanma.cloud.demo.data.dao;

import com.ebanma.cloud.demo.data.model.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    Long getIdByName(@Param("name") String name);

    Integer create(@Param("create") UserDO create);

    Integer modify(@Param("modify") UserDO modify);

    Integer delete(@Param("id") Long id);
}