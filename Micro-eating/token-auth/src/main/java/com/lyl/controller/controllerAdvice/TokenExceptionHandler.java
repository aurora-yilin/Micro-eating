package com.lyl.controller.controllerAdvice;

import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyl
 * @Date 2020/10/9 16:07
 */
@ControllerAdvice
public class TokenExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public ResultType myExpiredJwtException(ExpiredJwtException e){
        return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"token已过期",null);
    }

    @ResponseBody
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResultType myUnsupportedJwtException(UnsupportedJwtException e){
        return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"token格式不匹配",null);
    }

    @ResponseBody
    @ExceptionHandler(MalformedJwtException.class)
    public ResultType myMalformedJwtException(MalformedJwtException e){
        return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"非法token",null);
    }

    @ResponseBody
    @ExceptionHandler(SignatureException.class)
    public ResultType mySignatureException(SignatureException e){
        return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"token签名验证失败",null);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultType myIllegalArgumentException(IllegalArgumentException e){
        return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),"token解析异常",null);
    }

}