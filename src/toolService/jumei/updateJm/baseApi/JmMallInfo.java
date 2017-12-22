package toolService.jumei.updateJm.baseApi;

import bean.ShopBean;
import util.JacksonUtil;

import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JmMallInfo extends  JmBase{
    /**  API地址 修改商城sku信息*/
    private  static  final  String UPDATE_MALL_SKU_INFO="v1/htSku/updateSkuForMall";
    /** API地址 修改商城商品信息*/
    private  static  final  String UPDATE_MALL_INFO="v1/htMall/updateMallInfo";
    /** API地址 商城商品追加sku*/
    private  static  final  String ADD_MALL_SKU="v1/htSku/addMallSku";
    /* API地址 商城修改价格*/
    private  static  final  String UPDATE_MALL_PRICE="v1/htMall/updateMallPriceBatch";
    /** API地址 商城商品上下架 */
    private  static  final  String UPDATE_MALL_STATUS_BATCH="v1/htMall/updateMallStatusBatch";


    /**
     * 修改商城价格
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateMallPrice(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }

        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_MALL_PRICE, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }

    /**
     * 商城上下架
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateMallIsOnSale(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }

        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_MALL_STATUS_BATCH, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }

    /**
     * 修改商城sku信息
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateMallSkuInfo(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_MALL_SKU_INFO, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }

    /**
     * 修改商城信息
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateMallInfo(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_MALL_INFO, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }

    /**
     * 商城商品追加sku
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> addMallSku(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, ADD_MALL_SKU, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }

}
