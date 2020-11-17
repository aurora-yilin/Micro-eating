package com.lyl.controller.controllerAdvice;

import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.MyIOException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lyl
 * @Date 2020/9/28 17:19
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyIOException.class)
    public ResultType MyIOExceptionHandler(MyIOException e){
        return ResultType.SERVERERROR(CommonEnum.SERVERERROR.getCode(),e.getMsg(),null);
    }
}
