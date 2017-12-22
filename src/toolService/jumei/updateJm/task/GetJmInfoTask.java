package toolService.jumei.updateJm.task;

import bean.JmBean.*;
import bean.ShopBean;
import toolService.jumei.updateJm.baseApi.JmDealInfo;
import toolService.jumei.updateJm.baseApi.JmOrderInfo;
import toolService.jumei.updateJm.baseApi.JmProductInfo;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class GetJmInfoTask {

    private ShopBean shopBean= AllUtils.getShopBean("all_27");
    private ArrayList<String> Data = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
    private ArrayList<String> errorlist =new ArrayList<String>();
    private ArrayList<String> successList =new ArrayList<String>();

    /**
     * ProductId GetInfo
     * @throws Exception
     */
    public void getProductInfoByProductIdTask() throws  Exception{
        ArrayList<String> hidlist =new ArrayList<String>();
        ArrayList<String> spuskulist =new ArrayList<String>();
        JmProductInfo jmProductInfo=new JmProductInfo();
        for(String productId:Data){
            Map<String, Object> params = new HashMap<>();
            params.put("product_id", productId);
            params.put("fields","hash_ids,spus,foreign_language_name");
            try {
                JmGetProductInfoRes jmGetProductInfoRes = jmProductInfo.getProductById(shopBean, params);
                if (jmGetProductInfoRes != null) {
                    //hid
                    String hash_ids = jmGetProductInfoRes.getHash_ids();
                    hidlist.add(productId + "\t" + hash_ids+"\t"+jmGetProductInfoRes.getForeign_language_name());
                    //spu\sku
                    List<JmGetProductInfo_Spus> spulist = jmGetProductInfoRes.getSpus();
                    for (JmGetProductInfo_Spus spu : spulist) {
                        List<JmGetProductInfo_Spus_Sku> skuList = spu.getSku_list();
                        for (JmGetProductInfo_Spus_Sku sku : skuList) {
                            String spu_sku = productId + "\t" + sku.getBusinessman_code() + "\t" + spu.getSpu_no().toString() + "\t" + sku.getSku_no().toString() + "\t" + spu.getUpc_code() + "\t" + spu.getAttributes() + "\t" + spu.getSize() + "\t" + spu.getAbroad_price();
                            spuskulist.add(spu_sku);
                            System.out.println(spu_sku);
                        }
                    }
                }
            }catch(Exception e){
                errorlist.add(productId+"\t"+e);
            }
        }
        System.out.println("All ProductId Get Over!");
        FileReadWrite.outDataToFile(hidlist,"ProductIdGetJmHashId");
        FileReadWrite.outDataToFile(spuskulist,"ProductIdGetJmSkuSpu");
        if (errorlist.size()!=0){
            FileReadWrite.outDataToFile(errorlist,"ProductIdGetInfoError");
        }
    }

    /**
     * DealId GetInfo
     * @throws Exception
     */
    public void getDealInfoByDealIdTask() throws Exception{
        JmDealInfo jmDealInfo=new  JmDealInfo();
        ArrayList<String> DealInfoList=new ArrayList<>();
        ArrayList<String> skuInfoList=new ArrayList<>();
        DealInfoList.add("JumeiHashId\tproduct_id\tDeal是否可售\t品牌名称\t开始时间\t结束时间\t产品库名称\t长名称\t中名称\t短名称\t发货仓\t限购数");
        skuInfoList.add("JumeiHashId\t聚美SKU\t市场价\t团购价\tsku是否可用\t库存\t折扣");
        for(String jmHashId:Data){
            try {
                JmGetDealInfoRes jmGetDealInfoRes = jmDealInfo.getDealInfoRes(shopBean,jmHashId);
                if (jmGetDealInfoRes!=null){
                    DealInfoList.add(jmHashId+"\t"+jmGetDealInfoRes.getProduct_id()+"\t"+jmGetDealInfoRes.getDeal_status()+"\t"+jmGetDealInfoRes.getBrand_name()+"\t"+jmGetDealInfoRes.getStart_time()+"\t"+jmGetDealInfoRes.getEnd_time()+"\t"+jmGetDealInfoRes.getProduct_short_name()+"\t"
                            +jmGetDealInfoRes.getLong_name()+"\t"+jmGetDealInfoRes.getMedium_name()+"\t"+jmGetDealInfoRes.getShort_name()+"\t"+jmGetDealInfoRes.getShipping_system_id()+"\t"+jmGetDealInfoRes.getUser_purchase_limit());
                    List<JmGetSkuRes>skuInfoRes=jmGetDealInfoRes.getSku_list();
                    for(JmGetSkuRes skuInfo :skuInfoRes){
                        skuInfoList.add(jmHashId+"\t"+skuInfo.getSku_no()+"\t"+skuInfo.getMarket_price()+"\t"+skuInfo.getCustomers_price()+"\t"+skuInfo.getIs_enable()+"\t"+skuInfo.getStocks()+"\t"+skuInfo.getDiscount());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                errorlist.add(jmHashId+"\t"+e.toString());
            }
        }
        System.out.println("All JmHashId Get Over!");
        if(errorlist.size()!=0){
            System.out.println("有一些获取失败了");
            FileReadWrite.outDataToFile(errorlist,"Deal获取失败");
        }
        FileReadWrite.outDataToFile(DealInfoList,"JM页面Deal商品信息");
        FileReadWrite.outDataToFile(skuInfoList,"JM页面sku_no的信息");
    }

    public void getOrderInfoByOrderId() throws Exception{
        JmOrderInfo jmOrderInfo=new JmOrderInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", "1132735904");
        String res=jmOrderInfo.getOrderDetailById(shopBean,params);
        System.out.println(res);
    }
}
