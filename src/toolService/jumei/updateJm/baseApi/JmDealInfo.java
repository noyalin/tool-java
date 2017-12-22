package toolService.jumei.updateJm.baseApi;


import bean.JmBean.JmGetDealInfoRes;
import bean.ShopBean;
import util.JacksonUtil;
import util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JmDealInfo extends JmBase {

    /* api地址 - 国际POP - 延迟Deal结束时间 */
    private static final String URL_UPDATE_DEAL_ENDTIME = "v1/htDeal/updateDealEndTime";
    /* api地址 更新deal 地址*/
    private static final String UPDATE_DEAL_INFO="/v1/htDeal/update";
    /* api地址 修改价格 */
    private  static  final  String  UPDATE_DEAL_PRICE="/v1/htDeal/updateDealPriceBatch";
    /* api地址 再售*/
    private  static  final  String  COPY_DEAL="v1/htDeal/copyDeal";
    /*api地址 修改sku信息  */
    private  static  final  String  SKU_UPDATE="v1/htSku/update";
    /* api地址 添加sku*/
    private  static final String ADD_SKU="v1/htSku/add";
    /* api地址 sku上下架*/
    private  static final String SKU_IS_ENABLE="v1/htDeal/updateSkuIsEnable";
    /** API地址 特卖绑定商城*/
    private  static  final  String DEALTOMALL="v1/htProduct/dealToMall";


    /**
     * 国际POP - 延迟Deal结束时间
     *
     * @param shopBean    系统级参数
     * @param appParams
     * @return Map<String, Object> 处理结果
     */
    public Map<String, Object> updateDealEndTime(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        //check
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult = null;
        try {
            reqResult = reqJmApi(shopBean, URL_UPDATE_DEAL_ENDTIME, appParams);
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
     * 国际POP - 修改deal（特卖）信息
     * @param shopBean
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateDealInfo(ShopBean shopBean,  Map<String, Object> params) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_DEAL_INFO, params);
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
     * 国际POP - 批量更新deal价格
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateDealPrice(ShopBean shopBean, Map<String, Object> appParams ) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }

        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_DEAL_PRICE, appParams);
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
     * 国际POP - 复制deal（特卖）信息
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> copyDeal(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, COPY_DEAL, appParams);
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
     * 修改Deal-SkuInfo
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateDealSku(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, SKU_UPDATE, appParams);
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
     * 添加sku
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> addDealSku(ShopBean shopBean, Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }

        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, ADD_SKU, appParams);
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
     * sku 上下架
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> skuIsOnSale(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, SKU_IS_ENABLE, appParams);
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


    private static String DEAL_GET = "v1/htDeal/getDealByHashID";

    /**
     * 获取Deal Info
     * @param shopBean
     * @param jumei_hash_id
     * @return
     * @throws Exception
     */
    public JmGetDealInfoRes getDealInfoRes(ShopBean shopBean, String jumei_hash_id) throws Exception{
        Map<String,Object>params=new HashMap<>();
        params.put("jumei_hash_id",jumei_hash_id);
        params.put("fields","product_id,deal_status,product_short_name,sku_list,long_name,medium_name,short_name,brand_name,start_time,end_time,shipping_system_id,user_purchase_limit");
        String reqResult = reqJmApi(shopBean, DEAL_GET, params);
        JmGetDealInfoRes resultBean= JsonUtil.jsonToBean(reqResult, JmGetDealInfoRes.class);
        return resultBean;
    }

}
