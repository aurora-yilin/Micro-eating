package com.lyl.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Commodity
 *
 * @author lyl
 * @date 2020/12/14 15:26
 * @since 1.0.0
 **/
public class Commodity implements Serializable {
    private Integer commodityId;
    private String commodityName;
    private String commodityDetails;
    private String commodityPrice;
    private List<String> imagePath;
    private Evaluation evaluation;

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityId=" + commodityId +
                ", commodityName='" + commodityName + '\'' +
                ", commodityDetails='" + commodityDetails + '\'' +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", evaluation=" + evaluation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commodity commodity = (Commodity) o;
        return Objects.equals(commodityId, commodity.commodityId) &&
                Objects.equals(commodityName, commodity.commodityName) &&
                Objects.equals(commodityDetails, commodity.commodityDetails) &&
                Objects.equals(commodityPrice, commodity.commodityPrice) &&
                Objects.equals(imagePath, commodity.imagePath) &&
                Objects.equals(evaluation, commodity.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commodityId, commodityName, commodityDetails, commodityPrice, imagePath, evaluation);
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityDetails() {
        return commodityDetails;
    }

    public void setCommodityDetails(String commodityDetails) {
        this.commodityDetails = commodityDetails;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }
}
