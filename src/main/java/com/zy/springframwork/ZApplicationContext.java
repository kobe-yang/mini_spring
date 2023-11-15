package com.zy.springframwork;


import com.zy.springframwork.anno.*;
import com.zy.springframwork.anno.Scope;
import com.zy.springframwork.definition.BeanDefinition;
import com.zy.springframwork.in.ApplicationContextAware;
import com.zy.springframwork.in.BeanNameAware;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZApplicationContext {
    private Class configClass;

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private Map<String, Object> singletonObjects = new HashMap<>();


    public ZApplicationContext(Class configClass) {
        this.configClass = configClass;

        //1.扫描
        scan(configClass);
        
        //2.实例化
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (com.zy.springframwork.Scope.SINGLETON.equals(beanDefinition.getScope())  && !beanDefinition.getLazy()) {
                singletonObjects.put(beanName,createBean(beanName,beanDefinition));
            }
        }

        //3.初始化
        
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object o = clazz.newInstance();

            //依赖注入  autowired
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(o, bean);
                }
            }

            //aware处理
            if ( o instanceof BeanNameAware) {
                ((BeanNameAware) o).setBeanName(beanName);
            }

            if (o instanceof ApplicationContextAware) {
                ((ApplicationContextAware) o).setApplicationContext(this);
            }


            return o;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public Object getBean(String beanName) {

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null) {
            throw new NullPointerException();
        }

        if (com.zy.springframwork.Scope.SINGLETON.equals(beanDefinition.getScope())) {
            Object result = singletonObjects.get(beanName);
            if (result == null) {
                System.out.println("getBean----" + beanName);
                result = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, result);
            }
            return result;
        } else {
            return createBean(beanName, beanDefinition);
        }
    }
    

    private void scan(Class configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String scanPath = componentScan.value();
            scanPath = scanPath.replace(".", "/");
            // 扫描的是class文件，所以是 target下边的数据
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL resource = classLoader.getResource(scanPath);
           //1.扫描文件夹下边所有的 文件
            File file = new File(resource.getFile());
            List<File> classFile = new ArrayList<>();
            recursionFile(file, classFile);


            for (File f : classFile) {
                String absolutePath = f.getAbsolutePath();
                try {
                    //2.解析带有Component 注解的类
                    Class<?> clazz = classLoader.loadClass(absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).replace("/", "."));
                    if (clazz.isAnnotationPresent(Component.class)) {
                        // 扫描出来的类怎么存储 ？ 用Map value 又是什么呢？ Bean 还有单例 多例 还有懒加载非懒加载等 如何处理呢？ 用一个专门的类来存储
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setType(clazz);
                        if (clazz.isAnnotationPresent(Scope.class)) {
                            beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
                        } else {
                            beanDefinition.setScope(com.zy.springframwork.Scope.SINGLETON);
                        }

                        if (clazz.isAnnotationPresent(Lazy.class)) {
                            beanDefinition.setLazy(clazz.getAnnotation(Lazy.class).value());
                        } else {
                            beanDefinition.setLazy(false);
                        }

                        String beanName = clazz.getAnnotation(Component.class).value();
                        if (beanName.isEmpty()) {
                            //生成beanName 代码 Spring 内部这样使用
                            beanName = Introspector.decapitalize(clazz.getSimpleName());
                        }

                        beanDefinitionMap.put(beanName, beanDefinition);
                    }

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private void recursionFile(File file, List<File> classFile) {
        if (!file.isDirectory()) {
            classFile.add(file);
            return;
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            recursionFile(file1, classFile);
        }
    }
    
}
