package com.lyl.controller;

import com.lyl.common.ResultType;
import com.lyl.entity.Commodity;
import com.lyl.entity.Evaluation;
import com.lyl.enums.CommonEnum;
import com.lyl.service.CommodityService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * CommodityController
 *
 * @author lyl
 * @date 2020/12/14 14:31
 * @since 1.0.0
 **/
@RestController
@RequestMapping("/commodity")
public class CommodityController {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommodityController.class);

    @DubboReference
    CommodityService commodityService;

    /**
     * 获取商品的信息，根据url地址的不同返回单个商品的信息或者全部商品的信息
     * @param commodityId
     * @return
     */
    @GetMapping(value = {"/info/{commodityId}","/info"})
    public ResultType getCommodityInfo(@PathVariable(value = "commodityId",required = false)Integer commodityId){
        if(Objects.nonNull(commodityId)) {
            /*commodityId参数不为空的时候返回参数对应的商品的信息*/
            Commodity commodity = commodityService.selectCommodityInfoById(commodityId);
            if(Objects.isNull(commodity)){
                LOGGER.warn("Input parameter error，The error parameter is{}",commodityId);
                return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),
                        CommonEnum.CLIENTERROR.getMsg(),null);
            }else {
                LOGGER.info("Parameter {} is accessed",commodityId);
                return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(), CommonEnum.SUCCESS.getMsg(), commodity);
            }
        }
        else{
            /*当commodityId参数为空的时候，返回全部商品信息*/
            List<Commodity> commodities = commodityService.selectCommodityInfoAll();
            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),commodities);
        }
    }

    /**
     * 获取商品的相关评价信息
     * @param commodityId
     * @return
     */
    @GetMapping("/evaluation/{commodityId}")
    public ResultType getCommodityEvaluation(@PathVariable("commodityId")Integer commodityId){
        if(Objects.nonNull(commodityId)) {
            List<Evaluation> evaluations = commodityService.selectEvaluationByCommodityId(commodityId);
            if(Objects.isNull(evaluations) || evaluations.size()==0){
                LOGGER.warn("Input parameter error，The error parameter is{}",commodityId);
                return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),
                        CommonEnum.CLIENTERROR.getMsg(),null);
            }else {
                LOGGER.info("Parameter {} is accessed",commodityId);
                return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(), CommonEnum.SUCCESS.getMsg(), evaluations);
            }
        }else{
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),
                    CommonEnum.CLIENTERROR.getMsg(),null);
        }
    }

    /**
     * 添加商品信息及商品图片路径
     * @param commodity
     * @return
     */
    @PostMapping("/save")
    public ResultType saveCommodity(@RequestBody Commodity commodity){
        if (Objects.isNull(commodity)) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),
                    CommonEnum.CLIENTERROR.getMsg(),null);
        }else{
            Integer result = commodityService.saveCommodity(commodity);
            LOGGER.info("Added new products");
            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),result);
        }
    }

    @PostMapping("/save/evaluation")
    public ResultType saveEvaluation(@RequestBody Evaluation evaluation) throws DataAccessException{
        if (Objects.isNull(evaluation)) {
            return ResultType.CLIENTERROR(CommonEnum.CLIENTERROR.getCode(),
                    CommonEnum.CLIENTERROR.getMsg(),null);
        }else{
            Integer result = null;
            result = commodityService.saveEvaluationByCommodity(evaluation);
            LOGGER.info("Added a comment for {}",evaluation.getEvaluationBelongCommodity_id());
            return ResultType.SUCCESS(CommonEnum.SUCCESS.getCode(),CommonEnum.SUCCESS.getMsg(),result);
        }
    }
}
