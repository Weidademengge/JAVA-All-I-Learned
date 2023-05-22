package com.ebanma.cloud.demo.domain.service;

import com.ebanma.cloud.demo.data.dao.UserDao;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private IdGenerator idGenerator;
}