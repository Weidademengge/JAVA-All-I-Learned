package com.duym.usertestall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duym.usertestall.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author duym
 * @version $ Id: UserMapper, v 0.1 2023/04/13 15:09 duym Exp $
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

}
