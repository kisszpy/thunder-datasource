package com.yukens.datasource.options;

import com.yukens.datasource.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Map;

public interface Option2 {

    String TEMPLATE_INSERT = "insert into %s values (%s)";

    String TEMPLATE_UPDATE = "update %s set %s where %s";

     int INSERT = 1;

     int UPDATE = 2;

     int DELETE = 3;

     int SELECT = 4;


    default  <T> String sql(T object,int type) {
       switch (type) {
           case INSERT:
               return insertSql(object);
           case UPDATE:
               return updateSql(object);
            default:
               break;
       }
       return null;
    }

    default <T> String updateSql(T object){
        Class<?> clz = object.getClass();
        String tableName = clz.getSimpleName().toLowerCase();
        Map<String,Object> map = ReflectUtils.Object2Map(object);
        StringBuilder builder = new StringBuilder();
        for(String key : map.keySet()) {
            String fieldName = key;
            Object value = map.get(key);
            if (value instanceof  String) {
                builder.append(fieldName).append("='").append(map.get(key)).append("',");
            }else {
                builder.append(fieldName).append("=").append(map.get(key)).append(",");
            }
        }
        String id = ReflectUtils.getPrimaryKey(object);
        // todo this have a bug
        Object idValue = ReflectUtils.invoke(object,"getId");
        return String.format(TEMPLATE_UPDATE, tableName,builder.deleteCharAt(builder.lastIndexOf(",")),id+"="+idValue);
    }


    default <T> String insertSql(T object) {
        Class<?> clz = object.getClass();
        String tableName = clz.getSimpleName().toLowerCase();
        StringBuilder builder = new StringBuilder();
        Field[] fields = clz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                builder.append("?").append(",");
            }
            return String.format(TEMPLATE_INSERT, tableName,builder.deleteCharAt(builder.lastIndexOf(",")));
        }
        throw new RuntimeException("error");
    }

}
