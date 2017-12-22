package toolService.jumei.updateJm.baseApi;

import bean.JmBean.JmGetProductInfoRes;
import bean.ShopBean;
import util.JacksonUtil;
import util.JsonUtil;

import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JmProductInfo extends JmBase {

    //修改产品属性
    private static String PRODUCT_UPDATE = "v1/htProduct/update";
    /**
     * 修改商品属性
     * @param shopBean
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateJmProductInfo(ShopBean shopBean, Map<String, Object> params) throws Exception {
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, PRODUCT_UPDATE, params);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
            Map<String, Object> rslt = JsonUtil.jsonToMap(reqResult);
            return rslt;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }


    //修改spu信息
    private static String SPU_UPDATE = "v1/htSpu/update";

    /**
     * 修改spu信息
     * @param shopBean
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateJmSpuInfo(ShopBean shopBean,Map<String, Object> params) throws Exception {
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, SPU_UPDATE, params);
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

    //添加spu
    private static String addHtSpu = "v1/htSpu/add";

    /**
     *添加spu
     * @param shopBean
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> addHtSpu(ShopBean shopBean,Map<String, Object> params) throws Exception {
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, addHtSpu, params);
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

    //通过名称或者产品ID取得产品信息
    private static String PRODUCT_GET = "v1/htProduct/getProductByIdOrName";

    public JmGetProductInfoRes getProductById(ShopBean shopBean, Map<String, Object> params) throws Exception {
        String reqResult = reqJmApi(shopBean, PRODUCT_GET, params);
        JmGetProductInfoRes resultBean = JsonUtil.jsonToBean(reqResult, JmGetProductInfoRes.class);
        return resultBean;
    }

}
