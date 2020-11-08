package com.lyl.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyl.enums.CommonEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author lyl
 * @Date 2020/9/28 16:29
 */
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ResultType implements Serializable {

    private Boolean isok;
    private int code;
    private String msg;
    private Object data;

    public ResultType() {
    }

    public ResultType(Boolean isok, int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.isok = isok;
    }

    public ResultType(Boolean isok, int code, String msg) {
        this(isok,code,msg,null);
    }

    public Boolean getIsok() {
        return isok;
    }

    public void setIsok(Boolean isok) {
        this.isok = isok;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 用来将对象序列化的工具
     */
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static ResultType SUCCESS(int code,String msg,Object data){
        return new ResultType(true,code,msg,data);
    }

    /**
     * 通用的成功json格式
     * @param msg
     * @param data
     * @param resp
     */
    public static void SUCCESS(String msg,Object data,HttpServletResponse resp){
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.write(objectMapper
                    .writeValueAsString(new ResultType(true,CommonEnum.SUCCESS.getCode(),msg,data)));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }

    public static ResultType SERVERERROR(int code,String msg,Object data){
        return new ResultType(false,code,msg,data);
    }

    public static void SERVERERROR(String msg,Object data,HttpServletResponse resp){
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.write(objectMapper
                    .writeValueAsString(new ResultType(false,CommonEnum.SERVERERROR.getCode(),msg,data)));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }

    public static ResultType CLIENTERROR(int code,String msg,Object data){
        return new ResultType(false,code,msg,data);
    }

    public static void CLIENTERROR(String msg,Object data,HttpServletResponse resp){
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.write(objectMapper
                    .writeValueAsString(new ResultType(false,CommonEnum.CLIENTERROR.getCode(),msg,data)));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }

    public static void COMMONRESULT(Boolean isok,int code,String msg,Object data,HttpServletResponse resp){
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.write(objectMapper
                    .writeValueAsString(new ResultType(isok,code,msg,data)));
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }
}
