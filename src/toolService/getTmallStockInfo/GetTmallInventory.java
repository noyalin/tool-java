package toolService.getTmallStockInfo;

import bean.ShopBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemSellerGetRequest;
import com.taobao.api.response.ItemSellerGetResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/11/17.
 */
public  class GetTmallInventory {
    private String shopCart="";
    private static ShopBean shopBean= AllUtils.getShopBean("928_31");
    private static ArrayList<String> numiidList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");

    public static  void main(String[] args) {
        ArrayList<String>stockInfoList=new ArrayList<>();
        int index=1;
        for(String numid:numiidList){
            System.out.println("Start From "+index+" get:"+numid);
            TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
            ItemSellerGetRequest req = new ItemSellerGetRequest();
            req.setFields("num_iid,sku,cid,product_id");
            req.setNumIid(Long.parseLong(numid));
            ItemSellerGetResponse rsp = null;
            try {
                rsp = client.execute(req, shopBean.getAppSecret());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            if(rsp==null){
                stockInfoList.add(numid+"\t未获取到");
            }else if(rsp.isSuccess()){
                String cid = rsp.getItem().getCid().toString();
                if (null!=rsp.getItem().getSkus()) {
                    for(int j=0;j<rsp.getItem().getSkus().size();j++) {
                        String sku = rsp.getItem().getSkus().get(j).getOuterId() +"\t"+rsp.getItem().getSkus().get(j).getSkuId()+ "\t" + rsp.getItem().getSkus().get(j).getQuantity();
                        String code = rsp.getItem().getPropertyAlias();
                        String price = rsp.getItem().getSkus().get(j).getPrice();
                        stockInfoList.add(numid+"\t"+sku);
                    }
                }else {
                    stockInfoList.add(numid+"\t无sku");
                }
            }else {
                stockInfoList.add(numid+"\t获取未成功");
            }
            index++;
        }
        FileReadWrite.outDataToFile("F:/TG/stock/shopStockInfo.txt",stockInfoList);
    }
}
