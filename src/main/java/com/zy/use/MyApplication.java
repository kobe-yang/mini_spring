package com.zy.use;


import com.zy.springframwork.ZApplicationContext;
import com.zy.use.service.UserService;

public class MyApplication {

    public static void main(String[] args) {
        ZApplicationContext applicationContext = new ZApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
    }
}
