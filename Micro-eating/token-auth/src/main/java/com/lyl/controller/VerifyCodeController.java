package com.lyl.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.code.kaptcha.Producer;
import com.lyl.common.ResultType;
import com.lyl.enums.CommonEnum;
import com.lyl.exception.InputIsIllegalException;
import com.lyl.exception.MobileNumNotExistException;
import com.lyl.mapper.UserMapper;
import com.lyl.properties.RedisConstant;
import com.lyl.exception.MyIOException;
import com.lyl.utils.VerificationCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lyl
 * @Date 2020/9/28 14:58
 */
@Slf4j
@RestController
public class VerifyCodeController {

    /**
     * 验证码生成工具类
     */
    @Resource(name = "createImageVerificationCodeUtil")
    VerificationCodeUtil imageVerificationCodeUtil;

    @Resource(name = "createSmsCodeUtil")
    VerificationCodeUtil smsCodeUtil;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    UserMapper userMapper;

    /**
     * 验证码图片的请求接口
     * @param resp
     */
    @GetMapping("/vc.jpg")
    public void getVerifyCode(HttpServletResponse resp,@RequestParam("userName")String userName){
        if(StringUtils.isEmpty(userName)){
            throw new InputIsIllegalException(CommonEnum.CLIENTERROR.getCode(),"请输入用户名");
        }
        resp.setContentType("image/jpeg");
        Producer producer = imageVerificationCodeUtil.verifyCode();
        String text = producer.createText();
        redisTemplate.opsForValue().set(RedisConstant.imageCode+":"+userName,
                text,
                Integer.parseInt(RedisConstant.imageCodeExpire),
                TimeUnit.MINUTES);
        final BufferedImage image = producer.createImage(text);
        try(ServletOutputStream outputStream = resp.getOutputStream()){
            ImageIO.write(image,"jpg",outputStream);
        } catch (IOException e) {
            throw new MyIOException(CommonEnum.SERVERERROR.getCode(),"IO Exception");
        }
    }

    @PostMapping("/smsCode")
    public ResultType getSmsCode(@RequestParam("mobileNum") String mobileNum){
        if (StringUtils.isEmpty(mobileNum) || mobileNum.length()!=11){
            throw new InputIsIllegalException(CommonEnum.CLIENTERROR.getCode(),"手机号码为空或者不符合格式");
        }
        final Integer result = userMapper.judgeMobileNumIsExist(mobileNum);
        if (result != 1 || result == null) {
            throw new MobileNumNotExistException(200,"手机号未注册");
        }
        final Producer producer = smsCodeUtil.verifyCode();
        final String text = producer.createText();
        redisTemplate.opsForValue().set(RedisConstant.smsCode+":"+mobileNum,
                text ,
                Integer.parseInt(RedisConstant.smsCodeExpire),
                TimeUnit.MINUTES );
        //调用短信发送服务
        log.info("======================="+text);
        return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),"手机验证码发送成功",null);
    }
}
