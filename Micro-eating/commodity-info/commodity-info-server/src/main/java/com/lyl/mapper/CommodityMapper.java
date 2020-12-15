package com.lyl.mapper;

import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;
import com.lyl.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
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

    Integer saveCommodity(@Param("commodity") Commodity commodity);

    Integer saveCommodityImage(@Param("image")Image image) throws DataAccessException;

    Integer saveEvaluationByCommodity(@Param("evaluation")Evaluation evaluation);
}
