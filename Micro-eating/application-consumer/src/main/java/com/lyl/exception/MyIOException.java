package com.lyl.exception;

/**
 * @author lyl
 * @Date 2020/9/28 16:17
 */
public class MyIOException extends RuntimeException{

    private int code;
    private String msg;

    public MyIOException(int code,String msg){
        super(msg);
        this.code= code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
