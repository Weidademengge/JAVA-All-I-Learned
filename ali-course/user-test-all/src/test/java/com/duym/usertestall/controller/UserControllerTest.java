package com.duym.usertestall.controller;

import com.duym.usertestall.domain.common.PageQuery;
import com.duym.usertestall.domain.common.PageResult;
import com.duym.usertestall.domain.dto.UserDTO;
import com.duym.usertestall.domain.dto.UserQueryDTO;
import com.duym.usertestall.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setup(){
        userController = new UserController();
        ReflectionTestUtils.setField(userController,"userService",userService);
    }



    @Test
    public void should_invoke_user_service_to_save_given_success(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.save(userDTO)).thenReturn(1);
        userController.save(userDTO);
        verify(userService).save(any(UserDTO.class));
    }

    @Test
    public void should_invoke_user_service_to_save_given_fail(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.save(userDTO)).thenReturn(0);
        userController.save(userDTO);

        verify(userService).save(any(UserDTO.class));
    }

    @Test
    public void should_invoke_user_service_to_update_given_success(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.update(1L,userDTO)).thenReturn(1);
        userController.update(1L,userDTO);

        verify(userService).update(any(),any(UserDTO.class));
    }

    @Test
    public void should_invoke_user_service_to_update_given_fail(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.update(1L,userDTO)).thenReturn(0);
        userController.update(1L,userDTO);

        verify(userService).update(any(),any(UserDTO.class));
    }

    @Test
    public void should_invoke_user_service_to_delete_given_success(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.delete(1L)).thenReturn(1);
        userController.delete(1L);

        verify(userService).delete(any());
    }

    @Test
    public void should_invoke_user_service_to_delete_given_fail(){
        UserDTO userDTO = new UserDTO();
        // given
        Mockito.when(userService.delete(1L)).thenReturn(0);
        userController.delete(1L);

        verify(userService).delete(any());
    }

    @Test
    public void should_invoke_user_service_to_query_given_success(){
        UserQueryDTO userQueryDTO = new UserQueryDTO();
        PageResult<List<UserDTO>> pageResult = Mockito.mock(PageResult.class,Mockito.RETURNS_DEEP_STUBS);

        // given
        Mockito.doReturn(pageResult).when(userService).query(Mockito.any());
//        Mockito.when(userService.query(pageQuery)).thenReturn(pageResult);
        ReflectionTestUtils.setField(userController,"userService",userService);
        userController.query(1,10,userQueryDTO);

        verify(userService).query(Mockito.any());
    }
}