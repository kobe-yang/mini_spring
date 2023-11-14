package com.zy.use.service;


import com.zy.springframwork.anno.Autowired;
import com.zy.springframwork.anno.Component;

@Component
public class UserService  {
    @Autowired
    private OrderService orderService;

    public void test() {
        orderService.test();
    }
}
