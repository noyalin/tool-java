package toolService.jumei.updateJm.utils;

import util.JacksonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simin on 2017/10/25.
 */
public class JumeiUtil {
    /**
     * 修改价格的json（待命）
     * @param update
     * @return
     */
    public static String updatedealpriceJsonString(String update){
        String jumei_hash_id = update.substring(0, update.indexOf("\t"));
        String sku_price = update.substring(update.indexOf("\t") + 1, update.lastIndexOf("\t"));
        String jumei_sku_no = sku_price.substring(0, sku_price.indexOf("\t"));
        String market_price = sku_price.substring(sku_price.lastIndexOf("\t") + 1);
        String deal_price = update.substring(update.lastIndexOf("\t") + 1);
        String updatejson="{"+"\"jumei_sku_no\""+":"+jumei_sku_no+","+"\"jumei_hash_id\""+":"+"\""+jumei_hash_id+"\""+","+"\"market_price\""+":"+market_price+","+"\"deal_price\""+":"+deal_price+"}";
        return  updatejson;
    }
    /**
     *修改页面库存的json（待命）
     * @param update_data
     * @return
     */
    public static String updateStockJson(String update_data){
        String jumei_sku_no = update_data.substring(0, update_data.indexOf("\t"));
        String stock = update_data.substring(update_data.lastIndexOf("\t") + 1);
        String updatejson="{"+"\"jumei_sku_no\""+":"+jumei_sku_no+","+"\"stock\""+":"+"\""+stock+"\""+"}";
        return  updatejson;
    }
    /**
     * 商城价格json（待命）
     * @param update_data
     * @return
     */
    public static  String updateMallPriceJsonString(String update_data){
        String jumei_sku_no=update_data.substring(0,update_data.indexOf("\t"));
        String market_price=update_data.substring(update_data.indexOf("\t")+1,update_data.lastIndexOf("\t"));
        String mall_price=update_data.substring(update_data.lastIndexOf("\t")+1);
        String updatejson="";
        if(!market_price.equals("")) {
            updatejson = "{" + "\"jumei_sku_no\"" + ":" + jumei_sku_no + "," + "\"market_price\"" + ":" + market_price + "," + "\"mall_price\"" + ":" + mall_price + "}";
        }else {
            updatejson = "{" + "\"jumei_sku_no\"" + ":" + jumei_sku_no + "," + "\"mall_price\"" + ":" + mall_price + "}";
        }
        return  updatejson;
    }

    /**
     * 根据任务自组JSON
     * @param data
     * @param taskName
     * @return
     */
    public static  ArrayList<String> buildJobJson(ArrayList<String> data,String taskName) {
        ArrayList<String> res = new ArrayList<>();
        String updatejson = "";
        switch (taskName) {
            case "DealPriceJob":
                for(String update_data:data) {
                    String[] DealPrice = update_data.split("\\t");
                    updatejson = "{" + "\"jumei_sku_no\"" + ":" + DealPrice[1] + "," + "\"jumei_hash_id\"" + ":" + "\"" + DealPrice[0] + "\"" + "," + "\"market_price\"" + ":" + DealPrice[2] + "," + "\"deal_price\"" + ":" + DealPrice[3] + "}";
                    res.add(updatejson);
                }
                return groupJson(res);
            case "MallPriceJob":
                for(String update_data:data) {
                    String[] MallPrice = update_data.split("\\t");
                    if (!MallPrice[1].equals("")) {
                        updatejson = "{" + "\"jumei_sku_no\"" + ":" + MallPrice[0] + "," + "\"market_price\"" + ":" + MallPrice[1] + "," + "\"mall_price\"" + ":" + MallPrice[2] + "}";
                    } else {
                        updatejson = "{" + "\"jumei_sku_no\"" + ":" + MallPrice[0] + "," + "\"mall_price\"" + ":" + MallPrice[2] + "}";
                    }
                    res.add(updatejson);
                }
                return groupJson(res);
            case "UpdateStockJob":
                for(String update_data:data) {
                    String[] UpdateStock = update_data.split("\\t");
                    updatejson = "{" + "\"jumei_sku_no\"" + ":" + UpdateStock[0] + "," + "\"stock\"" + ":" + "\"" + UpdateStock[1] + "\"" + "}";
                    res.add(updatejson);
                }
                return groupJson(res);
            case "UpdateMallStatus":
                for(String update_data:data) {
                    String[] mall = update_data.split("\\t");
                    updatejson="{"+"\"jumei_mall_id\""+":"+mall[0]+","+"\"status\""+":"+"\""+mall[1]+"\""+"}";
                    res.add(updatejson);
                }
                return groupJson(res);
            default:
                return null;
        }
    }

    /**
     * 19条json-组为一个groupJson
     * @param jsonlist
     * @return
     */
    public static ArrayList<String> groupJson(ArrayList<String> jsonlist){
        ArrayList<String> GroupJsonList=new ArrayList<String>();
        String StrJson="";
        if(jsonlist.size()==0){
            System.out.println("自组Json的条数必须大于0");
        }else  {
            if(jsonlist.size()<19){
                for(String json:jsonlist){
                    StrJson=StrJson+json+",";
                }
                GroupJsonList.add("["+StrJson.substring(0,StrJson.lastIndexOf(","))+"]");
            }else {
                int limitlist=0;
                int last=1;
                for(String json:jsonlist){
                    if(limitlist==19){
                        GroupJsonList.add("["+StrJson.substring(0,StrJson.lastIndexOf(","))+"]");
                        StrJson=json+",";
                        last++;
                        limitlist=1;
                    }else if(last==jsonlist.size()){
                        StrJson=StrJson+json+",";
                        GroupJsonList.add("["+StrJson.substring(0,StrJson.lastIndexOf(","))+"]");
                    }else {
                        StrJson=StrJson+json+",";
                        limitlist++;
                        last++;
                    }
                }
                System.out.println("共"+GroupJsonList.size()+"组GroupJson需要处理");
            }
        }
        return GroupJsonList;
    }

    public static ArrayList<String> groupCopyDealInfo (ArrayList<String> dataList){
        ArrayList<String[]> ArrList=new ArrayList<String[]>();
        ArrayList<String> groupList=new ArrayList<String>();

        for(String data:dataList){
            String[] splitData=data.split("\\t");
            String[] a= new String[2];
            a[0]=splitData[0];
            a[1]=splitData[1]+","+splitData[2]+","+splitData[3]+","+splitData[4];
            ArrList.add(a);
        }
        for(int i=0;i<ArrList.size();i++){
            StringBuffer stringBuffer = new StringBuffer();
            String group=ArrList.get(i)[1];

            int interval=0;
            if(i==ArrList.size()-1){
                group=ArrList.get(i)[0]+"\t"+group;
                groupList.add(group);
                break;
            }
            for(int j=i+1;j<=ArrList.size();j++){
                if(ArrList.get(i)[0].equals(ArrList.get(j)[0])){
                    group=group+";"+ArrList.get(j)[1];
                    interval=interval+1;
                    if(j==ArrList.size()-1){
                        group=ArrList.get(i)[0]+"\t"+group;
                        groupList.add(group);
                        i=i+interval;
                        break;
                    }
                }else if((!ArrList.get(i)[0].equals(ArrList.get(j)[0]))&&i!=(ArrList.size()-1)){
                    group=ArrList.get(i)[0]+"\t"+group;
                    groupList.add(group);
                    i=i+interval;
                    break;
                }
            }
        }
        return groupList;
    }


    private static final String IMG_HTML = "<img src=\"%s\" alt=\"\" />";
    private static final String IMG_6_1="http://p12.jmstatic.com/open_api/gPop_131/001/channel/SN6_1.jpeg";
    private static final String IMG_6_2="http://p12.jmstatic.com/open_api/gPop_131/001/channel/SN6_2.jpeg";
    private static final StringBuffer IMG_SHOP=new StringBuffer();

    public  static  ArrayList<String> responseGroupData (ArrayList<String> dataList){
        ArrayList<String[]> ArrList=new ArrayList<String[]>();
        ArrayList<String> groupList=new ArrayList<String>();
//        StringBuffer IMG_SHOP= new StringBuffer();
        IMG_SHOP.append(String.format(IMG_HTML,IMG_6_1));
        IMG_SHOP.append(String.format(IMG_HTML,IMG_6_2));
        for(String data:dataList){
            String[] splitData=data.split("\\t");
            ArrList.add(splitData);
        }
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("description_images", "abc");
        for(int i=0;i<ArrList.size();i++){
            StringBuffer stringBuffer = new StringBuffer();
            String group=ArrList.get(i)[1];
            StringBuffer img =stringBuffer.append(String.format(IMG_HTML,group));

//            System.out.println(ArrList.get(i)[0]+"\t"+JacksonUtil.bean2Json(map));
            int interval=0;

            if(i==ArrList.size()-1){
                Map<String, String> map = new HashMap<String, String>();
                map.put("description_images", img.append(IMG_SHOP).toString());
                group=ArrList.get(i)[0]+"\t"+ JacksonUtil.bean2Json(map);
                groupList.add(group);
                break;
            }
            for(int j=i+1;j<=ArrList.size();j++){
                if(ArrList.get(i)[0].equals(ArrList.get(j)[0])){
                    img=stringBuffer.append(String.format(IMG_HTML,ArrList.get(j)[1]));
                    interval=interval+1;
                    if(j==ArrList.size()-1){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("description_images", img.append(IMG_SHOP).toString());
                        group=ArrList.get(i)[0]+"\t"+JacksonUtil.bean2Json(map);
                        groupList.add(group);
                        i=i+interval;
                        break;
                    }
                }else if((!ArrList.get(i)[0].equals(ArrList.get(j)[0]))&&i!=(ArrList.size()-1)){
//                    group=ArrList.get(i)[0]+"\t"+img.append(IMG_SHOP);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("description_images", img.append(IMG_SHOP).toString());
                    group=ArrList.get(i)[0]+"\t"+JacksonUtil.bean2Json(map);
                    groupList.add(group);
                    i=i+interval;
                    break;

                }
            }
        }

//        System.out.println(groupList);
        return groupList;
    }
}
