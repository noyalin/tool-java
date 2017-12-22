package toolService.jumei.updateJm.task;

import bean.ShopBean;
import toolService.jumei.updateJm.baseApi.JmMallInfo;
import toolService.jumei.updateJm.utils.JumeiUtil;
import util.AllUtils;
import util.FileReadWrite;
import util.JacksonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class UpdateMallJmTask  {

    JmMallInfo jmMallInfo=new JmMallInfo();
    private ShopBean shopBean= AllUtils.getShopBean("all_27");
    private ArrayList<String> Data = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
    private ArrayList<String> errorlist =new ArrayList<String>();
    private ArrayList<String> successList =new ArrayList<String>();

    /**
     * 修改Mall属性
     * @throws Exception
     */
    public void  updateMallTask() throws  Exception{
        for(String data:Data){
            String[] mall=data.split("\\t");
            try {
                Map<String, Object> map = new HashMap<>();
//                map.put("description_properties", mall[1]);
//                map.put("description_usage", mall[2]);
//                map.put("product_short_name", mall[3]);
//                map.put("address_of_produce", mall[4]);
//                map.put("before_date", mall[5]);
                map.put("suit_people", "时尚潮流人士");
//                map.put("special_explain", mall[7]);
//                map.put("search_meta_text_custom", mall[8]);
                String update_data = JacksonUtil.bean2Json(map);

                Map<String, Object> appParams = new HashMap<>();
                appParams.put("jumei_mall_id", mall[0]);
                appParams.put("update_data", update_data);
                Map<String, Object> ret =jmMallInfo.updateMallInfo(shopBean,appParams);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(mall[0]+ "\t修改商城信息失败了\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(mall[0]+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(mall[0]+ "\t修改商城信息成功");
                }
            }catch (Exception e){
                errorlist.add(mall[0]+ "\t修改商城信息异常");
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"修改商城商品信息失败");
        }
    }


    /**
     * 修改 Mall Price
     * @throws Exception
     */
    public void  updateMallPriceTask() throws Exception{
        ArrayList<String> updateMallPriceJsonList= JumeiUtil.buildJobJson(Data,"MallPriceJob");
        for(String updatejson:updateMallPriceJsonList){
            Map<String, Object> params = new HashMap<>();
            params.put("update_data", updatejson);
            try {
                Map<String, Object> ret =jmMallInfo.updateMallPrice(shopBean,params);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(updatejson + "\t商城修改价格失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println("商城修改价格成功\t"+updatejson);
                }
            }catch (Exception e){
                errorlist.add(updatejson+"\t商城修改价格异常");
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"商城修改价格失败");
            System.out.println(errorlist);
        }
    }
    //Mall-SkuUpdate

    /**
     * 修改Mall sku
     * @throws Exception
     */
    public void updateMallSkuTask() throws Exception{
        for(String data:Data) {
            String[] mallSku = data.split("\\t");
            Map<String, Object> appParams = new HashMap<>();
            appParams.put("jumei_sku_no", mallSku[0]);
            appParams.put("status", mallSku[1]);
            appParams.put("businessman_num", mallSku[2]);
            try {
                Map<String, Object> ret =jmMallInfo.updateMallSkuInfo(shopBean,appParams);
                if ("success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println( mallSku[0]+ "\t修改商城skuInfo成功");
                } else {
                    System.out.println( mallSku[0] + "\t失败了\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add( mallSku[0]+"\t"+JacksonUtil.bean2Json(ret.get("response")).toString());
                }
            }catch (Exception e){
                errorlist.add(mallSku[0]+"\t"+e);
            }
        }
        if(errorlist.size()!=0) {
            System.out.println("共"+errorlist.size()+"个sku修改失败");
            FileReadWrite.outDataToFile(errorlist, "修改商城sku失败");
        }
    }


    /**
     * Mall 添加sku
     * @throws Exception
     */
    public void addMallSkuTask() throws Exception{
        for(String data:Data){
            String[] skuInfo=data.split("\\t");
            Map<String ,String> map=new HashMap<>();
            map.put("businessman_num",skuInfo[1]);
            map.put("stocks","1");//追加sku到商城商品，当spu下有sku时，直接追加当前sku到商城商品从而忽略掉所传的sku_info信息. 接口根据spu自动匹配到对应的mall. 经测试该字段必须大于0
            map.put("mall_price",skuInfo[3]);
            map.put("market_price",skuInfo[2]);
            String sku_info=JacksonUtil.bean2JsonNotNull(map);

            Map<String, Object> appParams = new HashMap<>();
            appParams.put("jumei_spu_no", skuInfo[0]);
            appParams.put("sku_info", sku_info);
            try {
                Map<String, Object> ret =jmMallInfo.addMallSku(shopBean,appParams);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(skuInfo[0] + "\tMall添加sku失败" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuInfo[0] +"\t"+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(skuInfo[0] + "\tMall添加sku成功");
                }
            }catch (Exception e){
                errorlist.add(skuInfo[0]+"\t"+e);
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"商城追加sku失败");
            System.out.println(errorlist);
        }
    }


    /**
     * 修改Mall Status
     * @throws Exception
     */
    public void  updateMallIsOnSaleTask() throws Exception{
        ArrayList<String> mallStatusList=JumeiUtil.buildJobJson(Data,"UpdateMallStatus");
        for(String mall:mallStatusList ){
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("goods_json", mall);
                Map<String, Object> ret =jmMallInfo.updateMallIsOnSale(shopBean,appParams);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(mall + "\tMall上/下架失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(mall+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(mall+ "\tMall上/下架成功");
                }
            }catch (Exception e){
                errorlist.add(mall+ "\tMall上/下架异常");
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"商城上下架失败");
        }
    }
}
