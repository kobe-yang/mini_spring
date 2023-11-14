package com.zy.use.service;


import com.zy.springframwork.anno.Component;
import com.zy.springframwork.anno.Lazy;
import com.zy.springframwork.anno.Scope;



@Scope(com.zy.springframwork.Scope.SINGLETON)
@Component
@Lazy
public class OrderService {

    public void test(){
        System.out.println("orderService test -----");
    }
}
