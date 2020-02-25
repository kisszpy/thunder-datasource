package com.yukens.datasource.common;


public abstract class ObjectPool<T> implements Pool<T> {



    public Object get() {
        return null;
    }


    public void add(T object) {

    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }
}
