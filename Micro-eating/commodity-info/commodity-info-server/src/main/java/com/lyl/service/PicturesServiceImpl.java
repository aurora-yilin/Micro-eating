package com.lyl.service;

import com.lyl.properties.PictureConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.io.File;

/**
 * Pictures
 *
 * @author lyl
 * @date 2020/11/16 17:28
 * @since 1.0.0
 **/
@DubboService
@Slf4j
public class PicturesServiceImpl implements PicturesService{

    @Resource
    private PictureConstant pictureConstant;

    @Override
    public File getPicture(String pictureName) {
        File file = new File(pictureConstant.getPath() + pictureName);
        if (file.exists()) {
            return file;
        }else{
            return new File(pictureConstant.getDefaultPicture());
        }
    }
}
