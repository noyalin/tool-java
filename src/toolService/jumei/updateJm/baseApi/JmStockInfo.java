package toolService.jumei.updateJm.baseApi;

import bean.ShopBean;
import util.JacksonUtil;

import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JmStockInfo extends JmBase {
    /** API地址 同步库存 */
    private  static  final  String UPDATE_HTSTOCK_STOCKSYNC="HtStock/StockSync";
    /** API地址 同步库存*/
    private  static  final  String UPDATEDEALSTOCKBATCH="v1/htDeal/updateDealStockBatch";

    /**
     * 同步库存接口1
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateStock(ShopBean shopBean, Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATE_HTSTOCK_STOCKSYNC, appParams);
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
     * 同步库存接口2
     * @param shopBean
     * @param appParams
     * @return
     * @throws Exception
     */
    public Map<String, Object> updateDealStockBatch(ShopBean shopBean,Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }

        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, UPDATEDEALSTOCKBATCH, appParams);
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
