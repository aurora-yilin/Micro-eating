package com.lyl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * PictureConstant
 *
 * @author lyl
 * @date 2020/11/16 17:33
 * @since 1.0.0
 **/
@Component
@ConfigurationProperties(prefix = "pictures")
public class PictureConstant {

    private String path;

    private String defaultPicture;

    public String getDefaultPicture() {
        return defaultPicture;
    }

    public void setDefaultPicture(String defaultPicture) {
        this.defaultPicture = defaultPicture;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
