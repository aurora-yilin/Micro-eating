package com.lyl.controller;

import com.lyl.enums.CommonEnum;
import com.lyl.exception.MyIOException;
import com.lyl.service.PicturesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * PictureController
 *
 * @author lyl
 * @date 2020/11/16 18:01
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @DubboReference
    private PicturesService picturesService;

    @GetMapping("/getPicture/{pictureName}")
    public void getPicture(@PathVariable("pictureName") String pictureName, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        try {
//        读取图片
            File picture = picturesService.getPicture(pictureName);
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(picture));
            response.setContentType("image/png");
            outputStream = response.getOutputStream();
            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, "png", outputStream);
            }
        } catch (IOException e) {
            log.error("获取图片异常{}",e.getMessage());
            throw new MyIOException(CommonEnum.SERVERERROR.getCode(),"IO Exception");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                }catch (Exception e){
                    throw new MyIOException(CommonEnum.SERVERERROR.getCode(),"IO Exception");
                }
            }
        }
    }
}
