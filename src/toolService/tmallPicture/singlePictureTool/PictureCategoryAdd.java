package toolService.tmallPicture.singlePictureTool;

import bean.ShopBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.PictureCategoryAddRequest;
import com.taobao.api.response.PictureCategoryAddResponse;
import util.AllUtils;

/**
 * Created by simin on 2017/12/6.
 */
public class PictureCategoryAdd {
    private static ShopBean shopBean= AllUtils.getShopBean("928_31");
    private  static  long  DEFAULT_CID=1639216180815498361L;//test BPU_IMG
    //1639216032189415365

    public static void main(String[] args) {
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
        PictureCategoryAddRequest req = new PictureCategoryAddRequest();
        req.setPictureCategoryName("detail_test");
        req.setParentId(1639216180815498361L);
        PictureCategoryAddResponse rsp = new  PictureCategoryAddResponse();
        try {
            rsp=client.execute(req, shopBean.getAppSecret());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getPictureCategory().getPictureCategoryId()+"\t"+rsp.getPictureCategory().getPictureCategoryName());
    }
}
