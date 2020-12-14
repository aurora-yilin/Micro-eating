package com.lyl.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Evaluation
 *
 * @author lyl
 * @date 2020/12/14 15:41
 * @since 1.0.0
 **/
public class Evaluation implements Serializable {
    private Integer evaluationId;
    private String evaluationContent;
    private String evaluationBelongUser;
    private String evaluationDate;
    private String evaluationBelongCommodity_id;
    private List<String> evaluationImagePath;


    @Override
    public String toString() {
        return "Evaluation{" +
                "evaluationId=" + evaluationId +
                ", evaluationContent='" + evaluationContent + '\'' +
                ", evaluationBelongUser='" + evaluationBelongUser + '\'' +
                ", evaluationDate='" + evaluationDate + '\'' +
                ", evaluationBelongCommodity_id='" + evaluationBelongCommodity_id + '\'' +
                ", evaluationImagePath='" + evaluationImagePath + '\'' +
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
        Evaluation that = (Evaluation) o;
        return Objects.equals(evaluationId, that.evaluationId) &&
                Objects.equals(evaluationContent, that.evaluationContent) &&
                Objects.equals(evaluationBelongUser, that.evaluationBelongUser) &&
                Objects.equals(evaluationDate, that.evaluationDate) &&
                Objects.equals(evaluationBelongCommodity_id, that.evaluationBelongCommodity_id) &&
                Objects.equals(evaluationImagePath, that.evaluationImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evaluationId, evaluationContent, evaluationBelongUser, evaluationDate, evaluationBelongCommodity_id, evaluationImagePath);
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public String getEvaluationBelongUser() {
        return evaluationBelongUser;
    }

    public void setEvaluationBelongUser(String evaluationBelongUser) {
        this.evaluationBelongUser = evaluationBelongUser;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public String getEvaluationBelongCommodity_id() {
        return evaluationBelongCommodity_id;
    }

    public void setEvaluationBelongCommodity_id(String evaluationBelongCommodity_id) {
        this.evaluationBelongCommodity_id = evaluationBelongCommodity_id;
    }

    public List<String> getEvaluationImagePath() {
        return evaluationImagePath;
    }

    public void setEvaluationImagePath(List<String> evaluationImagePath) {
        this.evaluationImagePath = evaluationImagePath;
    }
}
