package com.duym.usertestall.service;

import com.duym.usertestall.domain.common.PageQuery;
import com.duym.usertestall.domain.common.PageResult;
import com.duym.usertestall.domain.dto.UserDTO;
import com.duym.usertestall.domain.dto.UserQueryDTO;

import java.util.List;



public interface UserService {

    /**
     * 新增
     * @param userDTO
     * @return
     */
    int save(UserDTO userDTO);

    /**
     * 更新
     * @param id
     * @param userDTO
     * @return
     */
    int update(Long id, UserDTO userDTO);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery);
}