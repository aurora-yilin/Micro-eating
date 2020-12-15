package com.lyl.service;

import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;
import com.lyl.entity.Image;
import com.lyl.mapper.CommodityMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * CommodityServiceImpl
 *
 * @author lyl
 * @date 2020/12/14 15:25
 * @since 1.0.0
 **/
@DubboService
public class CommodityServiceImpl implements CommodityService{

    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public Commodity selectCommodityInfoById(Integer commodityId) {
        return commodityMapper.selectCommodityInfoById(commodityId);
    }

    @Override
    public List<Evaluation> selectEvaluationByCommodityId(Integer commodityId) {
        return commodityMapper.selectEvaluationByCommodityId(commodityId);
    }

    @Override
    public List<Commodity> selectCommodityInfoAll() {
        return commodityMapper.selectCommodityInfoAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveCommodity(Commodity commodity) {
        Integer commodityResult = commodityMapper.saveCommodity(commodity);
        Integer imageResult = null;
        Image image = new Image();
        for (String imagePath : commodity.getImagePath()) {
            image.setImagePath(imagePath);
            image.setImageBelongUser("admin");
            image.setImageBelongCommodityId(commodity.getCommodityId());
            imageResult = commodityMapper.saveCommodityImage(image);
        }
        return Integer.sum(commodityResult,imageResult);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveEvaluationByCommodity(Evaluation evaluation) throws DataAccessException {
        Integer evaluationResult = commodityMapper.saveEvaluationByCommodity(evaluation);
        Integer imageResult = null;
        Image image = new Image();
        for (String imagePath : evaluation.getEvaluationImagePath()) {
            image.setImagePath(imagePath);
            image.setImageBelongUser(evaluation.getEvaluationBelongUser());
            image.setImageBelongCommodityId(Integer.parseInt(evaluation.getEvaluationBelongCommodity_id()));
            imageResult = commodityMapper.saveCommodityImage(image);
        }
        return Integer.sum(evaluationResult,imageResult);
    }
}
