package com.lyl.service;

import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;
import com.lyl.mapper.CommodityMapper;
import org.apache.dubbo.config.annotation.DubboService;

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
}