package com.wddmg.usertest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddmg.usertest.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author duym
 * @version $ Id: UserMapperMybatis, v 0.1 2023/03/16 17:18 duym Exp $
 */
@Mapper
public interface UserMapperMybatis extends BaseMapper<UserDO> {

}
