package com.yukens.datasource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class ResultWrapper {

    private ResultSet resultSet;

    private Connection connection;

}
