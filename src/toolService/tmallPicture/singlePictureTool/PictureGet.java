package toolService.tmallPicture.singlePictureTool;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.PicturePicturesGetRequest;
import com.taobao.api.response.PicturePicturesGetResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/8/25.
 */
public class PictureGet {

    private static String target = "928_31";
    protected static String URL = "http://gw.api.taobao.com/router/rest";

    public static void main(String[] args) {
        String[] appKey = AllUtils.getAppKey(target);
//        //sku信息获取  category信息
        ArrayList<String> purlList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        ArrayList<String>purlInfoList= new ArrayList<String>();
        for(String purl:purlList){
            System.out.println("此次要获取信息的url:\t" +purl);
            TaobaoClient client = new DefaultTaobaoClient(URL, appKey[0], appKey[1]);
            PicturePicturesGetRequest req = new PicturePicturesGetRequest();
            req.setUrls(purl);
            req.setIsHttps(true);
            PicturePicturesGetResponse rsp =new PicturePicturesGetResponse();
            try {
                rsp = client.execute(req,  appKey[2]);
            } catch (ApiException e) {
                e.printStackTrace();
                purlInfoList.add(purl+"\t获取失败！");
            }
            System.out.println(purl+"\t"+rsp.getPictures().get(0).getTitle()+"\t"+rsp.getPictures().get(0).getPictureId());
            purlInfoList.add(purl+"\t"+rsp.getPictures().get(0).getTitle()+"\t"+rsp.getPictures().get(0).getPictureId());
        }
        System.out.println(purlInfoList);
        FileReadWrite.outDataToFile("F:/TG/pictureInfo/pictureInfo.txt",purlInfoList);
        System.out.println("图片信息已取完!");
    }
}
