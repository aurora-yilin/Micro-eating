package com.lyl.service;

import java.io.File;

/**
 * PicturesService
 *
 * @author lyl
 * @date 2020/11/16 17:30
 * @since 1.0.0
 **/
public interface PicturesService {

    /**
     * 获取图片
     * @param pictureName
     * @return
     */
    File getPicture(String pictureName);

    /**
     * 上传图片
     * @return
     */
    File uploadPicture();
}
