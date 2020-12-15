package com.lyl.service;

import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;

import java.util.List;

/**
 * CommodityService
 *
 * @author lyl
 * @date 2020/12/14 15:24
 * @since 1.0.0
 **/
public interface CommodityService {

    /**
     * 通过商品的id来查找商品信息
     * @param commodityId
     * @return
     */
    Commodity selectCommodityInfoById(Integer commodityId);

    /**
     * 通过商品的id查找商品的评价信息
     * @param commodityId
     * @return
     */
    List<Evaluation> selectEvaluationByCommodityId(Integer commodityId);

    /**
     * 查找所有商品
     * @return
     */
    List<Commodity> selectCommodityInfoAll();

    /**
     * 添加商品
     * @param commodity
     * @return
     */
    Integer saveCommodity(Commodity commodity);
}
