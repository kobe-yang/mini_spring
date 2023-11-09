package com.zy.springframwork;

public enum Scope {
    SINGLETON(0,"单例"),
    MULTI(1,"多例"),;

    private int code;

    private String desc;

    Scope(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
