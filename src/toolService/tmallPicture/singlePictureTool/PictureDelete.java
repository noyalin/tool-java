package toolService.tmallPicture.singlePictureTool;

import bean.ShopBean;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.PictureDeleteRequest;
import com.taobao.api.request.PicturePicturesGetRequest;
import com.taobao.api.response.PictureDeleteResponse;
import com.taobao.api.response.PicturePicturesGetResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simin on 2017/9/20.
 */
public class PictureDelete {
//    private static String target = "928_31";
//    protected static String URL = "http://gw.api.taobao.com/router/rest";
    private static ShopBean shopBean= AllUtils.getShopBean("928_31");

    public static void main(String[] args) {
//        String[] appKey = AllUtils.getAppKey(target);
//        //sku信息获取  category信息
        ArrayList<String> purlList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        ArrayList<String>purlInfoList= new ArrayList<String>();
//        for(String purl:purlList){
//            System.out.println("此次要获取信息的url:\t" +purl);
//            TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
//            PicturePicturesGetRequest req = new PicturePicturesGetRequest();
//            req.setUrls(purl);
//            req.setIsHttps(true);
//            PicturePicturesGetResponse rsp =new PicturePicturesGetResponse();
//            try {
//                rsp = client.execute(req, shopBean.getAppSecret());
//            } catch (ApiException e) {
//                e.printStackTrace();
//                purlInfoList.add(purl+"\t获取失败！");
//            }
//            System.out.println(purl+"\t"+rsp.getPictures().get(0).getTitle()+"\t"+rsp.getPictures().get(0).getPictureId());
//            purlInfoList.add(rsp.getPictures().get(0).getPictureId().toString());
//        }
//        System.out.println(purlInfoList);
//        FileReadWrite.outDataToFile("F:/TG/pictureInfo/pictureInfo.txt",purlInfoList);
        System.out.println("图片信息已取完!下面开始删除图片空间图片：");
        if(purlList.size()<=100){
            String picId= Joiner.on(",").join(purlList);
            TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
            PictureDeleteRequest req = new PictureDeleteRequest();
            req.setPictureIds(picId);
            PictureDeleteResponse rsp = null;
            try {
                rsp = client.execute(req,shopBean.getAppSecret());
            } catch (ApiException e) {
                System.out.println(picId+"\t"+"删除异常");
                e.printStackTrace();
            }
//            Map<String, Object> ret=rsp.getBody().;
            if (rsp.getBody().contains("true")){
                System.out.println(picId+"\t"+"删除成功");
            }else {
                System.out.println(picId+"\t"+"删除失败");
            }
        }else {
            int temp=purlList.size()/100;
            for(int i=0;i<temp+1;i++) {
                List<String> tempList=new ArrayList<>();
                if(i<temp) {
                    tempList = purlList.subList(i * 100, (i + 1) * 100);
                }else {
                    tempList = purlList.subList(i * 100,purlList.size());
                }
                String picId = Joiner.on(",").join(tempList);
                TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
                PictureDeleteRequest req = new PictureDeleteRequest();
                req.setPictureIds(picId);
                PictureDeleteResponse rsp = null;
                try {
                    rsp = client.execute(req, shopBean.getAppSecret());
                } catch (ApiException e) {
                    System.out.println(picId + "\t" + "删除异常");
                    e.printStackTrace();
                }
//            Map<String, Object> ret=rsp.getBody().;
                if (rsp.getBody().contains("true")) {
                    System.out.println(picId + "\t" + "删除成功");
                } else {
                    System.out.println(picId + "\t" + "删除失败");
                }
            }
        }
    }
}
