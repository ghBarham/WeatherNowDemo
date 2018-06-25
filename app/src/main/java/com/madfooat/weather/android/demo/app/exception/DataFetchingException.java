package com.madfooat.weather.android.demo.app.exception;

public class DataFetchingException extends Exception {

    private String msg;
    private String code;

    public DataFetchingException(String message, String code){
        super(message);
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

}
