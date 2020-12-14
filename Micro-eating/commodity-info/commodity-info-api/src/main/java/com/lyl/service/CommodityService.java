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

    Commodity selectCommodityInfoById(Integer commodityId);

    List<Evaluation> selectEvaluationByCommodityId(Integer commodityId);
}
