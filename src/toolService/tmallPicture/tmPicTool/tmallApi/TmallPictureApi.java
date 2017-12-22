package toolService.tmallPicture.tmPicTool.tmallApi;

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

/**
 * Created by simin on 2017/12/13.
 */
public  class TmallPictureApi {

    /**
     * 图片上传
     * @param shopBean
     * @param path
     * @param title
     * @param category_id
     * @return
     */
    public static String uploadPicture(ShopBean shopBean,String path,String title,long category_id){
        System.out.println("上传天猫图片名称:\t" + title);
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
        PictureUploadRequest req = new PictureUploadRequest();
        req.setPictureCategoryId(category_id);
//            req.setImg(picBt);
        req.setImg(new FileItem(path + "imagebyte.txt"));
        req.setImageInputTitle(title + ".jpg");
//            req.setTitle("图片名称");
//            req.setPictureId(10000L);
//            req.setClientType("client:computer");
//            req.setIsHttps(true);
        PictureUploadResponse rsp = new PictureUploadResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
        } catch (ApiException e) {
            e.printStackTrace();
            return title + "\t上传失败";
        }
        if (null == rsp.getPicture() || null == rsp.getPicture().getPictureId() || null == rsp.getPicture().getPicturePath()) {
            System.out.println(title + "\t上传失败");
            return title + "\t上传失败";
        } else {
            System.out.println( title+"\t" + rsp.getPicture().getPictureId() + "\t" + rsp.getPicture().getTitle() + "\t" + rsp.getPicture().getPicturePath() + "\t上传成功！");
            return rsp.getPicture().getPictureId() + "\t" + rsp.getPicture().getTitle() + "\t" + rsp.getPicture().getPicturePath() + "\t上传成功！";
        }
    }

    /**
     * 图片信息获取
     * @param shopBean
     * @param title
     * @param category_id
     * @return
     */
    public static String getPictureInfo(ShopBean shopBean,String title,long category_id){
        System.out.println("获取天猫图片名称:\t" + title);
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(),shopBean.getAppKey(), shopBean.getSessionKey());
        PicturePicturesGetRequest req = new PicturePicturesGetRequest();
//        req.setUrls(purl);
        req.setTitle(title+".jpg");
        req.setPictureCategoryId(category_id);
        req.setIsHttps(true);
        PicturePicturesGetResponse rsp =new PicturePicturesGetResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
        } catch (ApiException e) {
            e.printStackTrace();
//            purlInfoList.add(purl+"\t获取失败！");
            System.out.println(title +"\t图片获取失败！");
            return (title +"\t图片获取失败！");
        }
        if(rsp.getPictures()!=null) {
            System.out.println(rsp.getPictures().get(0).getPictureId()+"\t"+rsp.getPictures().get(0).getTitle().replace(".jpg","")+"\t"+rsp.getPictures().get(0).getPicturePath() +"\t图片已存在！");
            return rsp.getPictures().get(0).getPictureId()+"\t"+rsp.getPictures().get(0).getTitle().replace(".jpg","")+"\t"+rsp.getPictures().get(0).getPicturePath()+"\t图片已存在！";
        }
        return "";
    }

    /**
     * 新增类目
     * @param shopBean
     * @param categoryName
     * @param parentId
     * @return
     */
    public  static long picCatrgoryAdd(ShopBean shopBean,String categoryName,long parentId){
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(), shopBean.getAppKey(), shopBean.getSessionKey());
        PictureCategoryAddRequest req = new PictureCategoryAddRequest();
        req.setPictureCategoryName(categoryName);
        req.setParentId(parentId);
        PictureCategoryAddResponse rsp = new  PictureCategoryAddResponse();
        try {
            rsp=client.execute(req, shopBean.getAppSecret());
            if(null!=rsp.getPictureCategory()){
                System.out.println(rsp.getPictureCategory().getPictureCategoryId()+"\t"+rsp.getPictureCategory().getPictureCategoryName()+"\t类目新增成功");
                return rsp.getPictureCategory().getPictureCategoryId();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return parentId;
    }

    /**
     * 图片类目获取
     * @param shopBean
     * @param picture_category_name
     * @param ParentId
     * @return
     */
    public static long picCategotyGet(ShopBean shopBean,String picture_category_name,long ParentId){
        System.out.println("获取天猫图片类目名称:\t" + picture_category_name);
        TaobaoClient client = new DefaultTaobaoClient(shopBean.getApp_url(),shopBean.getAppKey(), shopBean.getSessionKey());
        PictureCategoryGetRequest req = new PictureCategoryGetRequest();
//            req.setPictureCategoryId(100L);
        req.setPictureCategoryName(picture_category_name);
//            req.setType("类型");
        req.setParentId(ParentId);
//            req.setModifiedTime(StringUtils.parseDateTime("2011-01-01 11:11:11"));
        PictureCategoryGetResponse rsp = new PictureCategoryGetResponse();
        try {
            rsp = client.execute(req, shopBean.getAppSecret());
            if(rsp.getPictureCategories()!=null){
                for(int cids=0 ;cids<rsp.getPictureCategories().size();cids++) {
                    System.out.println(rsp.getPictureCategories().get(cids).getPictureCategoryId() +"\t"+rsp.getPictureCategories().get(cids).getPictureCategoryName()+"\t"+rsp.getPictureCategories().get(cids).getParentId()+"\t已存在该类目");
                }
                return rsp.getPictureCategories().get(0).getPictureCategoryId();//此次获取的是指定图片的父级类目，因此只有一个类目id。
            }
        } catch (ApiException e) {
            e.printStackTrace();
            System.out.println(picture_category_name + "\t获取失败！");
        }
        return  ParentId;
    }
}
