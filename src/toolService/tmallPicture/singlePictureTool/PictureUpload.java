package toolService.tmallPicture.singlePictureTool;

import bean.ShopBean;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.PictureCategoryAddRequest;
import com.taobao.api.request.PictureCategoryGetRequest;
import com.taobao.api.request.PicturePicturesGetRequest;
import com.taobao.api.request.PictureUploadRequest;
import com.taobao.api.response.PictureCategoryAddResponse;
import com.taobao.api.response.PictureCategoryGetResponse;
import com.taobao.api.response.PicturePicturesGetResponse;
import com.taobao.api.response.PictureUploadResponse;
import toolService.tmallPicture.tmPicTool.utils.Image2byte;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/12/5.
 */
public class PictureUpload {

    private static ShopBean shopBean= AllUtils.getShopBean("928_31");
    private static long PAREAT_ID=1639216180815498361L;
    private static long  DEFAULT_CID=1639216180815498361L;

    private static long MAIN_IMG_ID=1639216180890153597L;
    private static long DETAIL_IMG_ID=1639216182946168552L;
    private static String urlMoban_Main="http://s7d5.scene7.com/is/image/sneakerhead/1212%20tu?$800_800_new_test_file$&$image={urlkey}";//主图
//    private static String urlMoban_Detail="http://image.voyageone.com.cn/928/is/image/sneakerhead/{urlkey}?fmt=jpg&scl=1&qlt=100";//详情图  ?fmt=jpg&scl=1&qlt=100
    private static String urlMoban_Detail="http://s7d5.scene7.com/is/image/sneakerhead/liking-bpu-perfume?$800$&$layer_2_textps_0={brand}&$layer_3_textps_0={sex}&$layer_4_textps_0={capacity}&$layer_5_textps_0={fragrance}&$layer_6_textps_0={topNotes}&$layer_7_textps_0={middleNotes}&$layer_8_textps_0={afterNotes}&$layer_9_textps_0={description}";
    private static String urlMoban="";
    private  final static String path="F:/TG/uploadImage/";



    public static void main(String[] args) {
        //sku信息获取  category信息
        ArrayList<String> picInfoList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        ArrayList<String> successList = new ArrayList<>();
        ArrayList<String> failList = new ArrayList<>();
        String LastCatename="";
        String imgType="";
        long picCategoryId=DEFAULT_CID;
        for(String picInfo:picInfoList){
//            PictureBean pictureBean = new PictureBean();
            String res="";
            String[] picUrlInfoArr=picInfo.split("\\t");
            if(picUrlInfoArr[3].equals("main")){
                DEFAULT_CID=MAIN_IMG_ID;
                urlMoban=urlMoban_Main;
            }else if(picUrlInfoArr[3].equals("detail")){
                DEFAULT_CID=DETAIL_IMG_ID;
//                urlMoban=urlMoban_Detail;
                urlMoban= Image2byte.urlEncode(picUrlInfoArr,urlMoban_Detail);
            }
            if(picUrlInfoArr[1].length()>20){
                String name=picUrlInfoArr[1].substring(0,20);
                picUrlInfoArr[1]=name;
            }
            if(LastCatename.equals("") ||!LastCatename.equals(picUrlInfoArr[1])||!imgType.equals(picUrlInfoArr[3])) {
                picCategoryId = picCategotyGet(picUrlInfoArr[1],DEFAULT_CID);
                LastCatename=picUrlInfoArr[1];
                imgType=picUrlInfoArr[3];
            }
            if(picCategoryId!=DEFAULT_CID){
                if(picUrlInfoArr[3].equals("main")) {
                    res = getPictureByTitle(picUrlInfoArr[0], picCategoryId);
                }else {
                    res = getPictureByTitle(picUrlInfoArr[1], picCategoryId);
                }
            }else{
                picCategoryId = picCatrgoryAdd(picUrlInfoArr[1], DEFAULT_CID);
            }
            if(res.equals("")) {
                try {
                    res = pictureUpload(picInfo, picCategoryId);
                }catch (Exception e){
                    failList.add(res);
                }
                if(res.contains("失败")){
                    failList.add(res);
                }else {
                    successList.add(res);
                }
            }else{

                System.out.println(picInfo+ "\t"+res+"\t已存在，忽略！");
                successList.add(picInfo+ "\t"+res+"\t已存在");
            }
        }
        FileReadWrite.outDataToFile(path+"result/successUpload.txt",successList);
        System.out.println(failList.size() +"\t张图上传失败！");
        if(failList.size()>0){
            FileReadWrite.outDataToFile(path+"result/failUpload.txt",failList);
        }
    }

    public static  String  getPictureByTitle(String title,long categoryId){
//        PictureBean pictureBean =new PictureBean();
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(),shopBean.getAppKey(), shopBean.getSessionKey());
        PicturePicturesGetRequest req = new PicturePicturesGetRequest();
//        req.setUrls(purl);
        req.setTitle(title+".jpg");
        req.setPictureCategoryId(categoryId);
        req.setIsHttps(true);
        PicturePicturesGetResponse rsp =new PicturePicturesGetResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
        } catch (ApiException e) {
            e.printStackTrace();
//            purlInfoList.add(purl+"\t获取失败！");
        }
        if(rsp.getPictures()!=null) {
            System.out.println(title +"\t图片已存在！");
//            pictureBean.setTitle(rsp.getPictures().get(0).getTitle());
//            pictureBean.setPicPath(rsp.getPictures().get(0).getPicturePath());
//            pictureBean.setPicCid(rsp.getPictures().get(0).getPictureCategoryId().toString());
//            pictureBean.setPictureId(rsp.getPictures().get(0).getPictureId().toString());
//            pictureBean.setPixel(rsp.getPictures().get(0).getPixel());
//            pictureBean.setSize(rsp.getPictures().get(0).getSizes().toString());
            return rsp.getPictures().get(0).getPictureId()+"\t"+rsp.getPictures().get(0).getTitle().replace(".jpg","")+"\t"+rsp.getPictures().get(0).getPicturePath();
        }
        return "";
    }

    public static long picCatrgoryAdd(String categoryName,long parentId){
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
        PictureCategoryAddRequest req = new PictureCategoryAddRequest();
        req.setPictureCategoryName(categoryName);
        req.setParentId(parentId);
        PictureCategoryAddResponse rsp = new  PictureCategoryAddResponse();
        try {
            rsp=client.execute(req, shopBean.getAppSecret());
            if(null!=rsp.getPictureCategory()){
                System.out.println(rsp.getPictureCategory().getPictureCategoryId()+"\t"+rsp.getPictureCategory().getPictureCategoryName()+"\t类目新增成功");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp.getPictureCategory().getPictureCategoryId();
    }

    public static long picCategotyGet(String picture_category_name,long category_id){
        System.out.println("获取图片类目名称:\t" + picture_category_name);
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(),shopBean.getAppKey(), shopBean.getSessionKey());
        PictureCategoryGetRequest req = new PictureCategoryGetRequest();
//            req.setPictureCategoryId(100L);
        req.setPictureCategoryName(picture_category_name);
//            req.setType("类型");
        req.setParentId(category_id);
//            req.setModifiedTime(StringUtils.parseDateTime("2011-01-01 11:11:11"));
        PictureCategoryGetResponse rsp = new PictureCategoryGetResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
            if(rsp.getPictureCategories()!=null){
                for(int cids=0 ;cids<rsp.getPictureCategories().size();cids++) {
                    System.out.println(rsp.getPictureCategories().get(cids).getPictureCategoryId() +"\t"+rsp.getPictureCategories().get(cids).getPictureCategoryName()+"\t"+rsp.getPictureCategories().get(cids).getParentId());
                }
                return rsp.getPictureCategories().get(0).getPictureCategoryId();//此次获取的是指定图片的父级类目，因此只有一个类目id。
            }
        } catch (ApiException e) {
            e.printStackTrace();
            System.out.println(picture_category_name + "\t获取失败！");
        }
        return  category_id;
    }

    public static String pictureUpload(String picUrlInfo ,long category_id ) {
        String[] picUrlInfoArr = picUrlInfo.split("\\t");
        byte[] picBt = new byte[0];
        try {
            picBt=Image2byte.getImageFromNetByUrl(urlMoban.replace("{urlkey}",picUrlInfoArr[0]) );
            FileReadWrite.byteToFile(path + "imagebyte.txt", picBt);
        }catch (Exception e){
            e.printStackTrace();
            return  picUrlInfo + "\t上传失败";
        }
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
        PictureUploadRequest req = new PictureUploadRequest();
        req.setPictureCategoryId(category_id);
//            req.setImg(picBt);
        req.setImg(new FileItem(path + "imagebyte.txt"));
//        req.setImageInputTitle(picUrlInfoArr[0]  + ".jpg");
        if(picUrlInfoArr[3].equals("detail")){
            req.setImageInputTitle(picUrlInfoArr[1]  + ".jpg");
//            req.setTitle(picUrlInfoArr[1]);
        }else {
            req.setImageInputTitle(picUrlInfoArr[0]  + ".jpg");
//            req.setTitle(picUrlInfoArr[0]);
        }
//            req.setTitle("图片名称");
//            req.setPictureId(10000L);
//            req.setClientType("client:computer");
//            req.setIsHttps(true);
        PictureUploadResponse rsp = new PictureUploadResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
        } catch (ApiException e) {
            e.printStackTrace();
            return picUrlInfo + "\t上传失败";
        }
        if (null == rsp.getPicture() || null == rsp.getPicture().getPictureId() || null == rsp.getPicture().getPicturePath()) {
            System.out.println(picUrlInfo + "\t上传失败");
            return picUrlInfo + "\t上传失败";
        } else {
            System.out.println(picUrlInfo + "\t" + rsp.getPicture().getPictureId() + "\t" + rsp.getPicture().getTitle() + "\t" + rsp.getPicture().getPicturePath() + "\t上传成功！");
            return picUrlInfo + "\t" + rsp.getPicture().getPictureId() + "\t" + rsp.getPicture().getTitle() + "\t" + rsp.getPicture().getPicturePath() + "\t上传成功！";
        }
    }
}
