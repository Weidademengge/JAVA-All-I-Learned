package com.duym.myspring.service;

import com.duym.myspring.spring.*;

/**
 * @author duym
 * @version $ Id: UserService, v 0.1 2023/04/17 13:14 duym Exp $
 */

@Component
@Scope("prototype")
public class UserServiceImpl implements UserService{

    @Autowired
    private OrderService orderService;

    @Override
    public void test(){
        System.out.println(orderService);
    }


}
