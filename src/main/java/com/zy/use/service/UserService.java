package com.zy.use.service;


import com.zy.springframwork.ZApplicationContext;
import com.zy.springframwork.anno.Autowired;
import com.zy.springframwork.anno.Component;
import com.zy.springframwork.in.ApplicationContextAware;
import com.zy.springframwork.in.BeanNameAware;

@Component
public class UserService  implements BeanNameAware, ApplicationContextAware {
    @Autowired
    private OrderService orderService;

    private String beanName;

    private ZApplicationContext applicationContext;

    public void test() {
        System.out.println("beanName" + beanName);
        System.out.println(applicationContext);
        orderService.test();
    }
    @Override
    public void setApplicationContext(ZApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

}
