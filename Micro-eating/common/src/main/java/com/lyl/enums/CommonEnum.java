package com.lyl.enums;

/**
 * @author lyl
 * @Date 2020/9/28 16:22
 */
public enum CommonEnum {

    SUCCESS(200,"操作成功"),

    CLIENTERROR(400,"客户端错误"),

    SERVERERROR(500,"服务端错误");

    private int code;
    private String msg;

    private CommonEnum(int code,String msg){
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
