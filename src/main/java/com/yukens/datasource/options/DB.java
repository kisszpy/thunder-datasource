package com.yukens.datasource.options;

public enum DB implements Option2 {

    INSTANCE;

//    DataSource ds = new ThunderDataSource();

    Object target;

//    public Connection getConnection() {
//        try {
//            return ds.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public <T> DB save(T o) {
        System.out.println(sql(o,INSERT));
        return this;
    }

    public <T> DB update(T o) {
        System.out.println(sql(o,UPDATE));
        return this;
    }



}
