package com.lyl.mapper;

import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CommodityMapper
 *
 * @author lyl
 * @date 2020/12/14 15:38
 * @since 1.0.0
 **/
@Mapper
@Component
public interface CommodityMapper {
    Commodity selectCommodityInfoById(@Param("commodityId")Integer commodityId);

    List<Evaluation> selectEvaluationByCommodityId(@Param("commodityId")Integer commodityId);

    List<Commodity> selectCommodityInfoAll();
}
