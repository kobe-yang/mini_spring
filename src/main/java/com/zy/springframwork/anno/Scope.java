package com.zy.springframwork.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    com.zy.springframwork.Scope value() default com.zy.springframwork.Scope.SINGLETON;
}
