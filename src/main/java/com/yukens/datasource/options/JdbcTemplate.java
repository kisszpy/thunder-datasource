package com.yukens.datasource.options;


import com.yukens.datasource.ThunderDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JdbcTemplate {

    private DataSource dataSource = new ThunderDataSource();

    public void execute(String sql) throws SQLException {
        dataSource.getConnection().prepareStatement(sql);
    }


}
