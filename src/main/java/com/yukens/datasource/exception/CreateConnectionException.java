package com.yukens.datasource.exception;

/**
 * 创建连接异常
 */
public class CreateConnectionException extends RuntimeException {

    public CreateConnectionException(String message) {
        super(message);
    }
}
