package toolService.tmallPicture.tmPicTool.service;

import bean.ShopBean;
import toolService.tmallPicture.tmPicTool.tmallApi.TmallPictureApi;
import toolService.tmallPicture.tmPicTool.utils.Image2byte;
import toolService.tmallPicture.tmPicTool.utils.TmallPictureContant;
import util.AllUtils;
import util.FileReadWrite;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by simin on 2017/12/13.
 */
public class TmallPictureService {


    public void run() {
        ShopBean shopBean = AllUtils.getShopBean("928_31");
        ArrayList<String> picInfoList = FileReadWrite.dataList(TmallPictureContant.DataSources);
        ArrayList<String> successList = new ArrayList<>();
        ArrayList<String> failList = new ArrayList<>();
        String lastCateName = "";
        int isGetCategory = 0;
        Long parentId = TmallPictureContant.Parent_id;
        long childCategoryId = 0L;
//        String moban="";
        String imgType = "";
        for (String picInfo : picInfoList) {
            String res = "";
            String title = "";
            String url = "";
            String[] picInfoArr = picInfo.split("\\t");
            if (picInfoArr[4].equals("main")) {
                parentId = TmallPictureContant.MAIN_IMG_ID;
//                moban=TmallPictureContant.mobanMap.get(picInfoArr[4]);
                title = picInfoArr[0];
            } else if (picInfoArr[4].equals("detail")) {
                parentId = TmallPictureContant.DETAIL_IMG_ID_new;
//                moban=TmallPictureContant.mobanMap.get(picInfoArr[0]);
                //cms未上传的话就上传到 detail_img_new
                title = picInfoArr[2];
                if (!TmallPictureContant.paramMap.containsKey(picInfoArr[0])) {
                    //cms已上传的话就上传到 detail_img
                    title = picInfoArr[0];
                    parentId = TmallPictureContant.DETAIL_IMG_ID;
                }
            }
            //图片类目名称（文件夹名称）不得超过20个字符
            if (picInfoArr[2].length() > 20) {
                String name = picInfoArr[2].substring(0, 20);
                picInfoArr[2] = name;
            }
            //如果连续两条数据的图片类目名称和图片类型（主图、详情图）是一样的话，第二次就没必要获取了
            if (lastCateName.equals("") || !lastCateName.equals(picInfoArr[2]) || !imgType.equals(picInfoArr[4])) {
                childCategoryId = TmallPictureApi.picCategotyGet(shopBean, picInfoArr[2], parentId);
                lastCateName = picInfoArr[2];
                imgType = picInfoArr[4];
            }
            //在主图或者详情类目下新建code名称的图片类目
            if (childCategoryId != parentId) {
                if (picInfoArr[4].equals("main") || !TmallPictureContant.paramMap.containsKey(picInfoArr[0])) {
                    res = TmallPictureApi.getPictureInfo(shopBean, picInfoArr[0], childCategoryId);
                } else if(TmallPictureContant.paramMap.containsKey(picInfoArr[0])) {
                    res = TmallPictureApi.getPictureInfo(shopBean, picInfoArr[2], childCategoryId);
                }
            } else {
                childCategoryId = TmallPictureApi.picCatrgoryAdd(shopBean, picInfoArr[2], parentId);
            }
            byte[] picBt = new byte[0];
            if (res.equals("") && childCategoryId!=parentId) {
                try {
                    if (picInfoArr[4].equals("main")) {
                        url = TmallPictureContant.mobanMap.get("main");
                        for (int paraIndex = 0; paraIndex < TmallPictureContant.paramMap.get("main"); paraIndex++) {
                            // 主图
                            url = url.replace("{Parameter_" + (paraIndex + 1) + "}", URLEncoder.encode(picInfoArr[0], "utf-8"));
                        }
                    } else if (TmallPictureContant.paramMap.containsKey(picInfoArr[0])) {
                        url = TmallPictureContant.mobanMap.get(picInfoArr[0]);
                        for (int paraIndex = 0; paraIndex < TmallPictureContant.paramMap.get(picInfoArr[0]); paraIndex++) {
                            // 详情图有多个参数
                            url = url.replace("{Parameter_" + (paraIndex + 1) + "}", URLEncoder.encode(picInfoArr[paraIndex + 6], "utf-8"));
                        }
                    } else {
                        url = TmallPictureContant.mobanMap.get("detail");
                        for (int paraIndex = 0; paraIndex < TmallPictureContant.paramMap.get("detail"); paraIndex++) {
                            // cms已上传详情图
                            url = url.replace("{Parameter_" + (paraIndex + 1) + "}", URLEncoder.encode(picInfoArr[0], "utf-8"));
                        }
                    }
                    picBt = Image2byte.getImageFromNetByUrl(url);
                    FileReadWrite.byteToFile(TmallPictureContant.path + "imagebyte.txt", picBt);
                } catch (Exception e) {
                    e.printStackTrace();
                    failList.add(picInfoArr[0] + "\t" + picInfoArr[1] + "\t" + picInfoArr[2] + "\t" + picInfoArr[3] + "\t" + picInfoArr[4] + "\turl转二进制失败！");
                }

                try {
                    res = TmallPictureApi.uploadPicture(shopBean, TmallPictureContant.path, title, childCategoryId);
                } catch (Exception e) {
                    failList.add(res);
                }
                if (res.contains("失败")) {
                    failList.add(res);
                } else {
                    if(TmallPictureContant.paramMap.containsKey(picInfoArr[0])){
                        successList.add(picInfoArr[2] + "\t" + picInfoArr[0] + "\t" + picInfoArr[2] + "\t" + picInfoArr[3] + "\t" + picInfoArr[4] + "\t" + res);
                    }else {
                        successList.add(picInfoArr[0] + "\t" + picInfoArr[1] + "\t" + picInfoArr[2] + "\t" + picInfoArr[3] + "\t" + picInfoArr[4] + "\t" + res);
                    }
                }
            } else {
                successList.add(picInfoArr[0] + "\t" + picInfoArr[1] + "\t" + picInfoArr[2] + "\t" + picInfoArr[3] + "\t" + picInfoArr[4] + "\t" + res);
            }
        }

        FileReadWrite.outDataToFile(TmallPictureContant.path + "result/successUpload.txt", successList);
        System.out.println(failList.size() + "\t张图上传失败！");
        if (failList.size() > 0) {
            FileReadWrite.outDataToFile(TmallPictureContant.path + "result/failUpload.txt", failList);
        }
    }
}
