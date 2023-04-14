package com.duym.usertestall.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.duym.usertestall.domain.common.ErrorCode;
import com.duym.usertestall.domain.common.PageQuery;
import com.duym.usertestall.domain.common.PageResult;
import com.duym.usertestall.domain.common.Result;
import com.duym.usertestall.domain.dto.UserDTO;
import com.duym.usertestall.domain.dto.UserQueryDTO;
import com.duym.usertestall.domain.vo.UserVO;
import com.duym.usertestall.service.UserService;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * POST /api/user     * 新增用户
     * allEntries设为true，则方法调用后立即清空所有的缓存
     */
    @CacheEvict(cacheNames = "users-cache", allEntries = true)
    @PostMapping
    public Result save(@RequestBody UserDTO userDTO) {

        int save = userService.save(userDTO);

        if (save == 1) {
            return Result.success();
        } else {
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        }

    }

    /**
     * PUT /api/user/{id}     * 更新用户信息  
     */
    @PutMapping("/{id}")
    public Result update(@NotNull @PathVariable("id") Long id, @RequestBody UserDTO userDTO) {

        int update = userService.update(id, userDTO);

        if (update == 1) {
            return Result.success();
        } else {
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        }

    }

    /**
     * DELETE /api/user/{id}     * 删除用户信息  
     */
    @DeleteMapping("/{id}")
    public Result delete(@NotNull @PathVariable("id") Long id) {

        int delete = userService.delete(id);

        if (delete == 1) {
            return Result.success();
        } else {
            return Result.fail(ErrorCode.SYSTEM_ERROR);
        }

    }

    /**
     * GET     * 查询用户信息  
     */
    @Cacheable(cacheNames = "users-cache",key="#pageNo")
    @GetMapping
    public Result<PageResult> query(Integer pageNo, Integer pageSize, UserQueryDTO queryDTO) {
        if (logger.isInfoEnabled()) {
            logger.info("未使用缓存！");
        }
        // 构造查询条件  
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(queryDTO);

        // 查询  
        PageResult<List<UserDTO>> pageResult =
                userService.query(pageQuery);

        // 实体转换  
        List<UserVO> userVOList = Optional
                .ofNullable(pageResult.getData())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDTO -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userDTO, userVO);
                    // 对特殊字段做处理
                    userVO.setPassword("******");
                    return userVO;
                })
                .collect(Collectors.toList());

        // 封装返回结果  
        PageResult<List<UserVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setData(userVOList);

        return Result.success(result);
    }
}