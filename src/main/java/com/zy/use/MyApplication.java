package com.zy.use;


import com.zy.springframwork.ZApplicationContext;
import com.zy.use.service.OrderService;
import com.zy.use.service.UserService;

public class MyApplication {

    public static void main(String[] args) {
        ZApplicationContext applicationContext = new ZApplicationContext(AppConfig.class);
        OrderService orderService = (OrderService) applicationContext.getBean("orderService");
        System.out.println(orderService);
        OrderService orderService1 = (OrderService) applicationContext.getBean("orderService");
        System.out.println(orderService1);
    }
}
