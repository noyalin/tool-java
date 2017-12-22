package toolService.tmallPicture.singlePictureTool;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

import com.taobao.api.request.PictureCategoryGetRequest;
import com.taobao.api.response.PictureCategoryGetResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/12/5.
 */
public class PictureCIdGet {
    private static String target = "928_31";
    protected static String URL = "http://gw.api.taobao.com/router/rest";

    public static void main(String[] args) {
        String[] appKey = AllUtils.getAppKey(target);
//        //sku信息获取  category信息
        ArrayList<String> purlList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        ArrayList<String>purlInfoList= new ArrayList<String>();

        for(String cname:purlList) {
            System.out.println("此次要获取信息的类目名称:\t" + cname);
            TaobaoClient client = new DefaultTaobaoClient(URL, appKey[0], appKey[1]);
            PictureCategoryGetRequest req = new PictureCategoryGetRequest();
//            req.setPictureCategoryId(100L);
            req.setPictureCategoryName(cname);
//            req.setType("类型");
            req.setParentId(1639216180815498361L);
//            req.setModifiedTime(StringUtils.parseDateTime("2011-01-01 11:11:11"));
            PictureCategoryGetResponse rsp = new PictureCategoryGetResponse();
            try {
                rsp = client.execute(req, appKey[2]);
            } catch (ApiException e) {
                e.printStackTrace();
                purlInfoList.add(cname + "\t获取失败！");
            }
            for(int cids=0 ;cids<rsp.getPictureCategories().size();cids++) {
                System.out.println(rsp.getPictureCategories().get(cids).getPictureCategoryId() +"\t"+rsp.getPictureCategories().get(cids).getPictureCategoryName()+"\t"+rsp.getPictureCategories().get(cids).getParentId());
            }
        }
    }

}
