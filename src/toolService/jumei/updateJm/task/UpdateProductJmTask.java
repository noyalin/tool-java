package toolService.jumei.updateJm.task;

import bean.ShopBean;
import toolService.jumei.updateJm.baseApi.JmProductInfo;
import util.AllUtils;
import util.FileReadWrite;
import util.JacksonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public  class UpdateProductJmTask {

    JmProductInfo updateJmProductInfo =new JmProductInfo();
    private ShopBean shopBean= AllUtils.getShopBean("all_27");
    private ArrayList<String> Data = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
    private ArrayList<String> errorlist =new ArrayList<String>();
    private ArrayList<String> successList =new ArrayList<String>();
    /**
     * 修改产品属性
     * @throws Exception
     */
    public  void updateProductTask() throws  Exception{
        String jumei_product_id="";
        for(String updateData :Data){
            try {
                String[] data = updateData.split("\\t");
                jumei_product_id = data[0];
                Map<String, Object> p_Map = new HashMap<>();
                p_Map.put("name",data[1]);
//                p_Map.put("foreign_language_name",data[1]);
//                p_Map.put("normalImage",data[1]);
                String data_json= JacksonUtil.bean2Json(p_Map);

                Map<String, Object> params = new HashMap<>();
                params.put("jumei_product_id", jumei_product_id);
                params.put("update_data", data_json);
                Map<String, Object> ret = updateJmProductInfo.updateJmProductInfo(shopBean,  params);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(jumei_product_id + " \t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(jumei_product_id + JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(jumei_product_id + "\tSuccess");
                }
            }catch (Exception e){
                errorlist.add(jumei_product_id+"\t"+e);
            }
        }
        System.out.println(errorlist.size()+"组失败");
        if(errorlist.size()!=0){
            FileReadWrite.outDataToFile(errorlist,"修改产品基本属性失败");
        }
    }

    /**
     * 修改SPU属性
     * @throws Exception
     */
    public void updateJmSpuTask() throws Exception{
        for(String updateData:Data){
            String[] spudata=updateData.split("\\t");
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("jumei_spu_no", spudata[0]);
                Map<String, String> dataMap = new HashMap<>();
//                dataMap.put("upc_code", spudata[1]);
//                dataMap.put("property", "OTHER");
//                dataMap.put("size", spudata[3]);
                dataMap.put("attribute", spudata[1]);
//                dataMap.put("abroad_price", spudata[4]);
//                dataMap.put("normalImage",spudata[5]);
                dataMap.put("area_code", "19");
                String spu_data=JacksonUtil.bean2Json(dataMap);
                params.put("update_data", spu_data);
                Map<String, Object> ret = updateJmProductInfo.updateJmSpuInfo(shopBean,params);
                if(!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(spudata[0]+"\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(spudata[0]+"\t"+JacksonUtil.bean2Json(ret.get("response")).toString());
                }else{
                    System.out.println(spudata[0]+"\tSuccess");
                }
            }catch (Exception e){
                errorlist.add(spudata[0]+"\t异常失败");
                e.printStackTrace();
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"失败");
            System.out.println(errorlist);
            FileReadWrite.outDataToFile(errorlist,"修改产品子型号失败");
        }
    }


    /**
     * 添加spu
     * @throws Exception
     */
    public void addSpuTask() throws Exception{
        for(String addData:Data){
            String spuInfo[]=addData.split("\\t");
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("jumei_product_id", spuInfo[0]);
                params.put("upc_code", spuInfo[1]);
                params.put("property", "OTHER");
                params.put("size", spuInfo[3]);
                params.put("attribute", spuInfo[2]);
                params.put("abroad_price", spuInfo[4]);
                params.put("normalImage", spuInfo[5]);
                params.put("area_code", "19");
                Map<String, Object> ret = updateJmProductInfo.addHtSpu(shopBean,params);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(spuInfo[0] + "\t" + spuInfo[1] + " \t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(spuInfo[0] +"\t"+spuInfo[1] +"\t"+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println("添加spu\t" + spuInfo[0] + "\tSuccess\t"+((Map<String, Object>) ret.get("response")).get("spu_no") + "\t" + spuInfo[2]);
                    successList.add(spuInfo[0] + "\t" + spuInfo[1] + "\t" + ((Map<String, Object>) ret.get("response")).get("spu_no") + "\t" + spuInfo[2]);
                }
            }catch (Exception e){
                errorlist.add(spuInfo[0]+"\t"+spuInfo[1]+"\t异常失败");
            }
        }
        FileReadWrite.outDataToFile(successList,"产品库添加子型号成功");
        if(errorlist.size()!=0){
            System.out.println(errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"产品库添加子型号失败");
        }
    }
}
