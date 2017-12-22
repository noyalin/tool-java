package toolService.jumei.updateJm.task;

import bean.JmBean.JmUpdatePriceResultBean;
import bean.ShopBean;
import toolService.jumei.updateJm.baseApi.JmBase;
import toolService.jumei.updateJm.baseApi.JmDealInfo;
import toolService.jumei.updateJm.baseApi.JmMallInfo;
import toolService.jumei.updateJm.baseApi.JmStockInfo;
import toolService.jumei.updateJm.utils.DateTimeUtil;
import toolService.jumei.updateJm.utils.JumeiUtil;
import util.AllUtils;
import util.FileReadWrite;
import util.JacksonUtil;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class UpdateDealJmTask extends JmBase {

    JmDealInfo jmDealInfo =new JmDealInfo();
    private ShopBean shopBean= AllUtils.getShopBean("all_27");
    private ArrayList<String> Data = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
    private ArrayList<String> errorlist =new ArrayList<String>();
    private ArrayList<String> successList =new ArrayList<String>();

    /**
     * 修改Deal属性
     * @throws Exception
     */
    public void updateDealTask() throws Exception{
        for(String updateData:Data){
            String[] dealInfo=updateData.split("\\t");
            try {
                Map<String, Object> deal_Map = new HashMap<>();
//                deal_Map.put("description_properties", dealInfo[1]);
//                deal_Map.put("product_medium_name",dealInfo[1]);
//                deal_Map.put("description_usage", dealInfo[2]);
//                deal_Map.put("product_short_name", dealInfo[3]);
//                deal_Map.put("address_of_produce", dealInfo[4]);
//                deal_Map.put("before_date", dealInfo[5]);
//                deal_Map.put("suit_people", "时尚潮流人士");
//                deal_Map.put("special_explain", "1.本产品自美国直邮发货，物流周期7-13天左右。支持7天无理由退换货，需保证商品全新不影响二次销售。2.服饰配件类商品请保证吊牌完好。3.鞋类商品，退回无鞋盒或退回无外包装导致鞋盒破损等影响二次销售的情况，我们将不能为您办理退换货事宜。4.如有任何疑问，可通过以下方式联系我们，电话：13104637039。");
                deal_Map.put("jumei_sku_no", dealInfo[1]);
                String update_data = JacksonUtil.bean2Json(deal_Map);

                Map<String, Object> params = new HashMap<>();
                params.put("jumei_hash_id", dealInfo[0]);
                params.put("update_data", update_data);
                Map<String, Object> ret = jmDealInfo.updateDealInfo(shopBean,params);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(dealInfo[0] + "\t失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(dealInfo[0] + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println("Deal更新成功\t" + dealInfo[0]);
                }
            }catch (Exception e){
                errorlist.add(dealInfo[0]+"\t"+e);
                e.printStackTrace();
            }
        }
        System.out.println("共"+errorlist.size()+"失败");
        if(errorlist.size()!=0){
            FileReadWrite.outDataToFile(errorlist,"修改特卖属性失败");
        }
    }


    /**
     * Deal-SkuUpdate
     * @throws Exception
     */
    public void updateDealSkuTask() throws Exception{
        for(String updateData:Data){
            String[] skuInfo=updateData.split("\\t");
            try {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("businessman_num",skuInfo[2]);
                String update_data=JacksonUtil.bean2Json(dataMap);
                Map<String, Object> params = new HashMap<>();
                params.put("jumei_sku_no", skuInfo[1]);
                params.put("jumei_hash_id", skuInfo[0]);
                params.put("update_data", update_data);
                Map<String, Object> ret = jmDealInfo.updateDealSku(shopBean,params);// jumei_hash_id, jumei_sku_no, update_data
                if(!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(	"更新失败\t"+skuInfo[0]+"\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuInfo[0]+"\t"+skuInfo[1]+"\t"+skuInfo[2]+"\t"+JacksonUtil.bean2Json(ret.get("response")).toString());
                }else{
                    System.out.println(skuInfo[0]+"\t"+skuInfo[1]+"\tSuccess");
                }
            }catch (Exception e){
                errorlist.add(skuInfo[0]+"\t"+skuInfo[1]+"\t"+skuInfo[2]+"\t异常失败");
                e.printStackTrace();
            }
        }
        System.out.println("共" + errorlist.size() + "条失败");
        if(errorlist.size()!=0) {
            FileReadWrite.outDataToFile(errorlist,"修改特卖SKU失败");
            System.out.println(errorlist);
        }
    }


    /**
     * DealPriceUpdate
     * @throws Exception
     */
    public void updateDealPriceTask() throws Exception{
        ArrayList<String> updateDealPriceJsonList= JumeiUtil.buildJobJson(Data,"DealPriceJob");
        for(String updatejson:updateDealPriceJsonList){
            Map<String, Object> params = new HashMap<>();
            params.put("update_data", updatejson);
            try {
                Map<String, Object> ret = jmDealInfo.updateDealPrice(shopBean,params);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(JacksonUtil.bean2Json(ret.get("response")).toString());
                    JmUpdatePriceResultBean JMUpdatePriceResultBean = JsonUtil.jsonToBean(( JacksonUtil.bean2Json(ret.get("response")).toString()), JmUpdatePriceResultBean.class);
                    List<JmUpdatePriceResultBean.ErrorList> errorl = JMUpdatePriceResultBean.getErrorList();
                    for (JmUpdatePriceResultBean.ErrorList errorBean : errorl) {
                        errorlist.add(errorBean.getJumei_sku_no() + "\t" + errorBean.getErrcode() + "\t" + errorBean.getError_message());
                    }
                } else {
                    System.out.println("刷新价格成功\t" + updatejson);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(errorlist.size()!=0){
            System.out.println(errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"修改特卖价格失败");
        }
    }


    /**
     * Deal延期
     * @param time
     * @throws Exception
     */
    public void updateDealEndTimeTask(String time) throws  Exception{
        for(String jmHashId:Data){
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("jumei_hash_id", jmHashId);
                params.put("end_time", DateTimeUtil.getTime(time, "yyyy-MM-dd HH:mm:ss", 8).toString());
                Map<String, Object> ret = jmDealInfo.updateDealEndTime(shopBean, params);
                if (!"1".equalsIgnoreCase(ret.get("is_Success").toString())) {
                    System.out.println(jmHashId + "\t失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(jmHashId + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    System.out.println(jmHashId + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(jmHashId + "\t Success");
                }
            }catch (Exception e){
                errorlist.add(jmHashId+"\t"+e);
            }
        }
        System.out.println(errorlist.size()+"个延期失败");
        if(errorlist.size()!=0){
            FileReadWrite.outDataToFile(errorlist,"延期失败");
        }
    }

    /**
     * Deal 添加sku
     * file:hashId-JmSpu-sku-marketPrice-dealPrice/mallPrice
     * @throws Exception
     */
    public void addDealSkuTask() throws Exception{
        JmMallInfo jmMallInfo=new JmMallInfo();
        for(String addData:Data){
            boolean IsAdd=false;
            String[] skuInfo=addData.split("\\t");
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("jumei_spu_no", skuInfo[1]);
                appParams.put("jumei_hash_id", skuInfo[0]);
                appParams.put("businessman_num", skuInfo[2]);
                appParams.put("stocks", "0");
                appParams.put("deal_price", skuInfo[4]);
                appParams.put("market_price", skuInfo[3]);
                Map<String, Object> retDeal =jmDealInfo.addDealSku(shopBean,appParams);
                if(!"success".equalsIgnoreCase(retDeal.get("reason").toString())) {
                    System.out.println("Deal添加sku失败"+skuInfo[0]+"\t"+skuInfo[1]+"\t"+ JacksonUtil.bean2Json(retDeal.get("response")).toString());
                    errorlist.add(skuInfo[0]+"\t"+skuInfo[1]+"\t"+JacksonUtil.bean2Json(retDeal.get("response")).toString());
                    IsAdd=false;
                }else{
                    System.out.println("Deal添加sku成功"+"\t"+skuInfo[0]+"\t"+skuInfo[1]);
                    successList.add(skuInfo[0]+"\t"+skuInfo[1]+"\t"+skuInfo[2]+"\t"+ ((Map<String, Object>) retDeal.get("response")).get("sku_no") );
                    IsAdd=true;
                }
            }catch (Exception e){
                errorlist.add(skuInfo[0]+"\t"+skuInfo[1]+"\t"+skuInfo[2]+"\tDeal添加sku异常"+e);
                e.printStackTrace();
            }
            //添加商城
            if(IsAdd==true){
                try {
                    Map<String, String> map = new HashMap<>();
                    map.put("businessman_num", skuInfo[2]);
                    map.put("stocks", "1");//追加sku到商城商品，当spu下有sku时，直接追加当前sku到商城商品从而忽略掉所传的sku_info信息. 接口根据spu自动匹配到对应的mall. 经测试该字段必须大于0
                    map.put("mall_price", skuInfo[3]);
                    map.put("market_price", skuInfo[4]);
                    String sku_info = JacksonUtil.bean2JsonNotNull(map);

                    Map<String, Object> appParams = new HashMap<>();
                    appParams.put("jumei_spu_no", skuInfo[0]);
                    appParams.put("sku_info", sku_info);
                    Map<String, Object> retMall = jmMallInfo.addMallSku(shopBean, appParams);
                    if (!"success".equalsIgnoreCase(retMall.get("reason").toString())) {
                        System.out.println(skuInfo[0] + "\tMall添加sku失败" + JacksonUtil.bean2Json(retMall.get("response")).toString());
                        errorlist.add(skuInfo[0] + "\t" + JacksonUtil.bean2Json(retMall.get("response")).toString());
                    } else {
                        System.out.println(skuInfo[0] + "\tMall添加sku成功");
                    }
                } catch (Exception e) {
                    errorlist.add(skuInfo[0] + "\tMall添加sku异常" + e);
                }
            }
        }
        FileReadWrite.outDataToFile(successList,"Deal&Mall添加sku成功");
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"条添加sku失败");
            FileReadWrite.outDataToFile(errorlist,"Deal&Mall添加sku失败");
        }
    }

    /**
     * Deal sku上下架
     * @throws Exception
     */
    public void skuIsOnSaleTask() throws  Exception{
        for(String data:Data){
            String[] skuInfo=data.split("\\t");
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("jumei_hash_id", skuInfo[0]);
                appParams.put("jumei_sku_no", skuInfo[1]);
                appParams.put("is_enable", skuInfo[2]);//  "1"是上架，"0"是下架

                Map<String, Object> ret=jmDealInfo.skuIsOnSale(shopBean,appParams);
                if (!"1".equalsIgnoreCase(ret.get("is_Success").toString())) {
                    System.out.println(skuInfo[0]+"\t"+skuInfo[1] + "\tDeal上下架sku失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuInfo[0]+"\t"+skuInfo[1] +skuInfo[1] + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println( skuInfo[0]+"\t"+skuInfo[1]+"\tSuccess");
                }
            }catch (Exception e){
                e.printStackTrace();
                errorlist.add(skuInfo[0]+"\t"+skuInfo[1] + "\tDeal上下架sku异常");
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组失败");
            FileReadWrite.outDataToFile(errorlist,"Deal上下架sku失败");
        }
    }


    /**
     * Deal 再售
     * @param startTime
     * @param endTime
     * @throws Exception
     */
    public  void copyDealTask(String startTime,String endTime) throws Exception{
        ArrayList<String> gropuList =JumeiUtil.groupCopyDealInfo(Data);
        for(String data:gropuList){
            ArrayList<String> skuStockList=new ArrayList<>();
            ArrayList<String> copySkuList=new ArrayList<>();
            String[] copyInfo=data.split("\\t");
            String[] copySku =null;
            if(copyInfo[1].contains(";")){
                copySku = copyInfo[1].split(";");
            }
            for (int i=0;i<copySku.length;i++){
                Map<String, Object> skusMap = new HashMap<>();
                String[] sku=copySku[i].split(",");
                skusMap.put("sku_no",sku[0]);
                skusMap.put("stocks","1");
                skusMap.put("deal_price",sku[2]);
                skusMap.put("market_price",sku[1]);
                String skus_json=JacksonUtil.bean2Json(skusMap);
                copySkuList.add(skus_json);
                skuStockList.add(sku[0]+"\t0");
            }
            String skus_data="";
            for(int i=0;i<copySkuList.size();i++){
                if(i==0){
                    skus_data="["+copySkuList.get(i);
                    if(copySkuList.size()==1){
                        skus_data=skus_data+"]";
                    }
                }else  if(copySkuList.size()>1 && i!=copySkuList.size()-1&&i!=0){
                    skus_data=skus_data+","+copySkuList.get(i);
                } else if(i==copySkuList.size()-1){
                    skus_data=skus_data+","+copySkuList.get(i)+"]";
                }
            }
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("jumei_hash_id", copyInfo[0]);
                appParams.put("start_time", startTime);
                appParams.put("end_time", endTime);
                appParams.put("skus_data",skus_data);
                Map<String, Object> ret=jmDealInfo.copyDeal(shopBean,appParams);
                if(!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println(copyInfo[0]+"\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(copyInfo[0]+"\t"+JacksonUtil.bean2Json(ret.get("response")).toString());
                }else{
                    System.out.println(copyInfo[0]+" Success");
                    successList.add(copyInfo[0]+"\t"+((Map<String, Object>)ret.get("response")).get("jumei_hash_id"));
                }
                copyDealAfterUpdateStockTask(skuStockList, copyInfo[0],"UpdateStockJob");
            }catch (Exception e){
                errorlist.add(copyInfo[0]+"\t再售异常"+e);
            }
        }
        FileReadWrite.outDataToFile(successList,"Deal再售成功");
        if(errorlist.size()!=0) {
            System.out.println("共"+errorlist.size()+"条失败");
            FileReadWrite.outDataToFile(errorlist, "Deal再售失败");
        }
    }

    public  void copyDealAfterUpdateStockTask(ArrayList<String> skuStockList, String hashId,String jobName) throws Exception{
        JmStockInfo jmStockInfo=new JmStockInfo();
        ArrayList<String>stockSkuJsonList= JumeiUtil.buildJobJson(skuStockList,jobName);
        for(String skuStock:stockSkuJsonList){
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("update_data", skuStock);
                Map<String, Object> ret =jmStockInfo.updateDealStockBatch(shopBean,appParams);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println("批量库存同步失败："+"\t"+skuStock + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuStock+"\t"+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println("批量库存同步成功：\t"+skuStock);
                }
            }catch (Exception e){
                errorlist.add(skuStock+"\t"+e);
            }
        }
        System.out.println(hashId+"\t库存同步为0成功！");
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组库存同步失败");
            FileReadWrite.outDataToFile(errorlist,"再售成功后批量库存同步失败");
        }
    }
}
