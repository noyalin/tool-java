package toolService.getOrderInfo;

import bean.ShopBean;
import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simin on 2017/7/5.
 */
public class GetTradeInfoFromTmall {
    private static ShopBean shopBean= AllUtils.getShopBean("928_31");
    public static void main(String[] args) {

        List<String> orderList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");

        TaobaoClient client = new AutoRetryTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(),shopBean.getSessionKey(), "xml");

        ArrayList<String> orderInfo=new ArrayList<>();
        for (String order : orderList) {

            TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
            req.setFields("tid,type,status,payment,orders,buyer_nick,created");
            req.setTid(Long.parseLong(order));
            TradeFullinfoGetResponse rsp = null;

            try {
                rsp = client.execute(req, shopBean.getAppSecret());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            if (rsp.isSuccess()) {
                String name=rsp.getTrade().getBuyerNick();
                SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=rsp.getTrade().getCreated();
                List<Order> orders = rsp.getTrade().getOrders();
                orders.parallelStream().forEach(o -> {
                    System.out.println(o.getOid() + "\t" + o.getOuterIid() + "\t"+name + "\t" + o.getNumIid() + "\t" + o.getPayment()+"\t"+o.getOuterSkuId()+"\t"+bartDateFormat.format(date));
                    orderInfo.add(o.getOid() + "\t"+o.getOuterSkuId() + "\t"+ o.getOuterIid() + "\t" + o.getNumIid() + "\t"+name +"\t"+ o.getPayment()+"\t"+bartDateFormat.format(date));
//                    FileUtils.writeToDisk(o.getOid() + "\t" + o.getOuterIid() + "\t" + o.getNumIid() + "\t" + o.getPayment(), "target");
                });
                FileReadWrite.outDataToFile("F:/TG/orderInfo/Order_info.txt",orderInfo);
            }

        }
    }

}
