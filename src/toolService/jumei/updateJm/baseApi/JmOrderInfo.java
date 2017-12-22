package toolService.jumei.updateJm.baseApi;

import bean.ShopBean;
import util.JacksonUtil;

import java.util.Map;

/**
 * Created by simin on 2017/12/15.
 */
public class JmOrderInfo extends JmBase {
    /**订单*/
    private  static  final  String GETORDERDETAILBYORDERID="HtOrder/GetOrderDetailByOrderId";

    public String getOrderDetailById(ShopBean shopBean, Map<String, Object> appParams) throws Exception {
        if (shopBean == null) {
            logger.warn("参数不能为空!");
            throw new IllegalArgumentException("参数不能为空!");
        }
        String reqResult=null;
        try {
            reqResult = reqJmApi(shopBean, GETORDERDETAILBYORDERID, appParams);
        } catch (Exception e) {
            logger.error("调用底层接口时发生错误", e);
            throw e;
        }
        if (reqResult == null) {
            logger.warn("reqResult is null");
            throw new Exception("reqResult is null");
        }
        try {
//            Map<String, Object> rslt = JacksonUtil.jsonToMap(reqResult);
            return reqResult;
        } catch (Exception e) {
            logger.error("解析结果时发生错误", e);
            throw e;
        }
    }
}
