package com.zy.springframwork.definition;

import com.zy.springframwork.Scope;

public class BeanDefinition {

    private Class type;
    private Scope scope;
    private Boolean lazy;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }
}
