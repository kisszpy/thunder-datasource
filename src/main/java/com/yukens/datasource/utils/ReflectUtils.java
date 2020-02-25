package com.yukens.datasource.utils;

import com.yukens.datasource.annotation.PrimaryKey;
import com.yukens.datasource.constant.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtils {

    private static final String GET = "get";

    private static final String SET = "set";


    public static Field[] getFields(Object o) {
        return o.getClass().getDeclaredFields();
    }

    public static Method getMethod(Object o, String methodName) {
        try {
            return o.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object invoke(Object o, String method, Object... params) {
        try {
            Method invoker = getMethod(o, method);
            return invoker.invoke(o, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> Object2Map(Object o) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = getFields(o);
        for (int i = 0; i < fields.length; i++) {
            String methodName = GET + fields[i].getName().substring(0, 1).toUpperCase() + fields[i].getName().substring(1);
            Object object = invoke(o, methodName);
            result.putIfAbsent(fields[i].getName(), object);
        }
        return result;
    }

    public static String getPrimaryKey(Object o) {
        for (int i = 0; i < getFields(o).length; i++) {
            PrimaryKey primaryKey = getFields(o)[i].getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                return primaryKey.name() .equals(Constant.EMPTY_STRING) ? getFields(o)[i].getName() : primaryKey.name();
            }
        }
        return null;
    }


}
