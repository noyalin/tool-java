package toolService.tmallPicture.tmPicTool.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simin on 2017/12/13.
 */
public final class TmallPictureContant {
    //data sources
    public static String DataSources="F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt";
    //BPU_IMG
    public static long Parent_id=1639216180815498361L;
    //BPU_IMG>MAIN_IMG
    public static long MAIN_IMG_ID=1639216180890153597L;
    //BPU_IMG>DETAIL_IMG_new
    public static long DETAIL_IMG_ID_new=1639216182946168552L;
    //BPU_IMG>DETAIL_IMG
    public static long DETAIL_IMG_ID=1639216180470263947L;

    public static String path="F:/TG/uploadImage/";

    public static  Map<String,String> mobanMap=new HashMap<String,String>();
    static {
        //主图
        mobanMap.put("main","http://s7d5.scene7.com/is/image/sneakerhead/1212%20tu?$800_800_new_test_file$&$image={Parameter_1}");
        //cms已上传详情用的模板
        mobanMap.put("detail","http://image.voyageone.com.cn/928/is/image/sneakerhead/{Parameter_1}?fmt=jpg&scl=1&qlt=100");
        //香水类目详情
//        mobanMap.put("Perfume","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dperfume?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}");
        mobanMap.put("Perfume","http://s7d5.scene7.com/is/image/sneakerhead/liking-bpu-perfume?$800$&$layer_11_textps_0={Parameter_8}&$layer_3_textps_0={Parameter_1}&$layer_4_textps_0={Parameter_2}&$layer_5_textps_0={Parameter_3}&$layer_6_textps_0={Parameter_4}&$layer_7_textps_0={Parameter_5}&$layer_8_textps_0={Parameter_6}&$layer_9_textps_0={Parameter_7}");
        //包
        mobanMap.put("Bag","");
        //鞋类
        mobanMap.put("Shoes","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dshoes?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}&$layer_11_textps_0={Parameter_10}&$layer_12_textps_0={Parameter_11}&$layer_13_textps_0={Parameter_12}&$layer_14_textps_0={Parameter_13}&$layer_15_textps_0={Parameter_14}&$layer_16_textps_0={Parameter_15}&$layer_17_textps_0={Parameter_16}&$layer_18_textps_0={Parameter_17}");
        //女士上装 裙装、男装
        mobanMap.put("TopApparel","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dcloths?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}&$layer_11_textps_0={Parameter_10}&$layer_12_textps_0={Parameter_11}&$layer_13_textps_0={Parameter_12}&$layer_14_textps_0={Parameter_13}");
        //裤装（男女）
        mobanMap.put("Pants","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dpants?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}&$layer_11_textps_0={Parameter_10}");
        //珠宝配饰
        mobanMap.put("JewelryAccessories","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Djewelry?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}&$layer_11_textps_0={Parameter_10}&$layer_12_textps_0={Parameter_11}");
        //太阳镜、眼镜
        mobanMap.put("Glasses","");
        //内衣
        mobanMap.put("Underwear","");
        //服饰配件（领带、帽子、围巾）
        mobanMap.put("ApparelAccessories","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Daccessory?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}");
        //个护
        mobanMap.put("PersonalCare","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dwash?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_9_textps_0={Parameter_7}&$layer_10_textps_0={Parameter_8}");
        //手表
        mobanMap.put("Watch","http://s7d5.scene7.com/is/image/sneakerhead/liking%2Dbpu%2Dwatches?$800$&$layer_2_textps_0={Parameter_1}&$layer_3_textps_0={Parameter_2}&$layer_4_textps_0={Parameter_3}&$layer_5_textps_0={Parameter_4}&$layer_6_textps_0={Parameter_5}&$layer_7_textps_0={Parameter_6}&$layer_8_textps_0={Parameter_7}&$layer_9_textps_0={Parameter_8}&$layer_10_textps_0={Parameter_9}&$layer_11_textps_0={Parameter_10}&$layer_12_textps_0={Parameter_11}&$layer_13_textps_0={Parameter_12}&$layer_14_textps_0={Parameter_13}");
    }

    public static  Map<String,Integer> paramMap=new HashMap<String,Integer>();
    static {
        paramMap.put("main",1);//主图
        paramMap.put("detail",1);//cms已上传详情
        paramMap.put("Perfume",8);//香水类目详情
        paramMap.put("Bag",0);//包
        paramMap.put("Shoes",17); //鞋类
        paramMap.put("TopApparel",13);//女士上装 裙装、男装
        paramMap.put("Pants",10);//裤装（男女）
        paramMap.put("JewelryAccessories",11);//珠宝配饰
        paramMap.put("Glasses",0); //太阳镜、眼镜
        paramMap.put("Underwear",0); //内衣
        paramMap.put("ApparelAccessories",9);//服饰配件（领带、帽子、围巾）
        paramMap.put("PersonalCare",8);//个护
        paramMap.put("Watch",13); //手表
    }
}
