package com.yukens.datasource.common;

public interface Pool<T> {


    <T> T get();

    void add(T object);

    int size();

    boolean isEmpty();

}
