package toolService.jumei.updateJm.task;

import bean.ShopBean;
import toolService.jumei.updateJm.baseApi.JmStockInfo;
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
public class UpdateStockJmTask  {

    JmStockInfo jmStockInfo=new JmStockInfo();
    private ShopBean shopBean= AllUtils.getShopBean("all_27");
    private ArrayList<String> Data = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
    private ArrayList<String> errorlist =new ArrayList<String>();

    /**
     * 批量同步库存
     * @throws Exception
     */
    public  void updateStockBatchTask() throws Exception{
        ArrayList<String>stockSkuJsonList= JumeiUtil.buildJobJson(Data,"UpdateStockJob");
        for(String skuStock:stockSkuJsonList){
            try {
                Map<String, Object> appParams = new HashMap<>();
                appParams.put("update_data", skuStock);
                Map<String, Object> ret =jmStockInfo.updateDealStockBatch(shopBean,appParams);
                if (!"success".equalsIgnoreCase(ret.get("reason").toString())) {
                    System.out.println("批量库存同步失败："+"\t"+skuStock + "\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuStock+"\t"+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println("批量同步库存成功： \t"+skuStock);
                }
            }catch (Exception e){
                errorlist.add(skuStock+"\t"+e);
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"组库存同步失败");
            FileReadWrite.outDataToFile(errorlist,"批量库存同步失败");
        }
    }

    /**
     * 单例同步库存
     * @throws Exception
     */
    public void  updateStockTask() throws Exception{
        for(String data:Data){
            String[] skuStock=data.split("\\t");
            Map<String, Object> appParams = new HashMap<>();
            appParams.put("businessman_code", skuStock[0]);
            appParams.put("enable_num", skuStock[1]);
            try {
                Map<String, Object> ret =jmStockInfo.updateStock(shopBean,appParams);
                if (!"success!".equalsIgnoreCase(ret.get("message").toString())) {
                    System.out.println(skuStock[0] + "\t单个sku修改库存失败\t" + JacksonUtil.bean2Json(ret.get("response")).toString());
                    errorlist.add(skuStock[0]+ JacksonUtil.bean2Json(ret.get("response")).toString());
                } else {
                    System.out.println(skuStock[0]+ "\t单个sku修改库存成功");
                }
            }catch (Exception e){
                errorlist.add(skuStock[0]+"\t"+e);
            }
        }
        if(errorlist.size()!=0){
            System.out.println("共"+errorlist.size()+"个sku修改库存失败");
            FileReadWrite.outDataToFile(errorlist,"单个sku修改库存成功");
        }
    }
}
