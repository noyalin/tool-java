package toolService.priceShow;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TmallItemUpdateSchemaGetRequest;
import com.taobao.api.response.TmallItemUpdateSchemaGetResponse;
import util.AllUtils;
import util.FileReadWrite;

/**
 * Created by simin on 2017/6/12.
 */
public class PriceDisclosure {

    public  static String uri="http://gw.api.taobao.com/router/rest";

    public static void main(String[] args) {

    }

    public String tmallItemUpdateSchameGet(String shop_id,String product_id, boolean isMainProudct){
        String schameXml="";
        String[] shop= AllUtils.getAppKey(shop_id);
        TaobaoClient client = new DefaultTaobaoClient(uri, shop[0], shop[1]);
        TmallItemUpdateSchemaGetRequest req = new TmallItemUpdateSchemaGetRequest();
//        req.setItemId(2100529818899L);
//        req.setCategoryId(1512L);
        req.setProductId(Long.parseLong(product_id));
        try {
            TmallItemUpdateSchemaGetResponse rsp = client.execute(req, shop[2]);
            schameXml=  rsp.getUpdateItemResult();
            FileReadWrite.saveSchame(schameXml,product_id,isMainProudct);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return schameXml;
    }
}
