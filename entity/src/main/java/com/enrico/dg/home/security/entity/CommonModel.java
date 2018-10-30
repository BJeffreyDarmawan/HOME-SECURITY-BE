package com.enrico.dg.home.security.entity;

public class CommonModel {

    public CommonModel() {
    }

    public boolean equals(Object obj) {
        return ObjectHelper.equals(this, obj);
    }

    public int hashCode() {
        return ObjectHelper.hashCode(this);
    }
}
