//package com.yukens.datasource.options;
//
//import java.lang.reflect.Field;
//
//public abstract class Option {
//
//    String TEMPLATE_INSERT = "insert into %s values (%s)";
//
//    String TEMPLATE_UPDATE = "update %s set (%s) where %s";
//
//    private static final int INSERT = 1;
//
//    private static final int UPDATE = 2;
//
//    private static final int DELETE = 3;
//
//    private static final int SELECT = 4;
//
//
//    protected <T> String sql(T object,int type) {
//       switch (type) {
//           case INSERT:
//               return insertSql(object);
//           case UPDATE:
//               return updateSql(object);
//            default:
//               break;
//       }
//       return null;
//    }
//
//    protected <T> String updateSql(T object){
//        return null;
//    }
//
//
//    protected <T> String insertSql(T object) {
//        Class<?> clz = object.getClass();
//        String tableName = clz.getSimpleName().toLowerCase();
//        StringBuilder builder = new StringBuilder();
//        Field[] fields = clz.getDeclaredFields();
//        if (fields != null && fields.length > 0) {
//            for (int i = 0; i < fields.length; i++) {
//                builder.append("?").append(",");
//            }
//            return String.format(TEMPLATE_INSERT, tableName,builder.deleteCharAt(builder.lastIndexOf(",")));
//        }
//        throw new RuntimeException("error");
//    }
//
//}
