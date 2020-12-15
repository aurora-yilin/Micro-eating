package com.lyl.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Image
 *
 * @author lyl
 * @date 2020/12/15 15:35
 * @since 1.0.0
 **/
public class Image implements Serializable {
    private Integer imageId;
    private String imagePath;
    private String imageBelongUser;
    private Integer imageBelongCommodityId;
    private String imageUploadDate;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageBelongUser() {
        return imageBelongUser;
    }

    public void setImageBelongUser(String imageBelongUser) {
        this.imageBelongUser = imageBelongUser;
    }

    public Integer getImageBelongCommodityId() {
        return imageBelongCommodityId;
    }

    public void setImageBelongCommodityId(Integer imageBelongCommodityId) {
        this.imageBelongCommodityId = imageBelongCommodityId;
    }

    public String getImageUploadDate() {
        return imageUploadDate;
    }

    public void setImageUploadDate(String imageUploadDate) {
        this.imageUploadDate = imageUploadDate;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", imagePath='" + imagePath + '\'' +
                ", imageBelongUser='" + imageBelongUser + '\'' +
                ", imageBelongCommodityId='" + imageBelongCommodityId + '\'' +
                ", imageUploadDate='" + imageUploadDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(imageId, image.imageId) &&
                Objects.equals(imagePath, image.imagePath) &&
                Objects.equals(imageBelongUser, image.imageBelongUser) &&
                Objects.equals(imageBelongCommodityId, image.imageBelongCommodityId) &&
                Objects.equals(imageUploadDate, image.imageUploadDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, imagePath, imageBelongUser, imageBelongCommodityId, imageUploadDate);
    }
}
