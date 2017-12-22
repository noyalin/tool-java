
package toolService.getTmallStockInfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.taobao.top.schema.exception.TopSchemaException;
import com.taobao.top.schema.factory.SchemaReader;
import com.taobao.top.schema.field.Field;
import com.taobao.top.schema.field.MultiComplexField;
import com.taobao.top.schema.value.ComplexValue;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;
import util.AllUtils;
import util.FileReadWrite;

import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TMallSKUold {
    private static String target = "001_23";
    private static File fileName = new File("C:\\temp\\" + target + "_skuinfo.txt");
    protected static String URL = "http://gw.api.taobao.com/router/rest";


    public static void main(String[] args) {
    	String[] appKey = AllUtils.getAppKey(target);
//        //sku信息获取  category信息
        ArrayList<String> numIIDList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        BufferedReader buf;
        Date date = new Date();
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String numIID : numIIDList) {
            System.out.println(numIID);
            TaobaoClient client = new DefaultTaobaoClient(URL, appKey[0], appKey[1]);
            ItemSellerGetRequest req = new ItemSellerGetRequest();
            req.setFields("num_iid,sku,cid,product_id");
            req.setNumIid(Long.parseLong(numIID));
            ItemSellerGetResponse rsp = null;
            try {
                rsp = client.execute(req, appKey[2]);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            if (null == rsp) {
                System.out.println(numIID + "\tFailed.");
                writeToDisk(dateFormat.format(date) + "\t" + numIID + "\tFailed.", target + "-" + fd.format(date) + "-" + "sku");
            } else if (rsp.isSuccess()) {
                String cid = rsp.getItem().getCid().toString();
                if (null!=rsp.getItem().getSkus()) {
                     for(int j=0;j<rsp.getItem().getSkus().size();j++) {
                        String sku = rsp.getItem().getSkus().get(j).getOuterId() + "\t" + rsp.getItem().getSkus().get(j).getQuantity()+"\t"+rsp.getItem().getSkus().get(j).getSkuId();
                        String code = rsp.getItem().getPropertyAlias();
                         String price = rsp.getItem().getSkus().get(j).getPrice();
                         writeToDisk(  numIID + "\t" +sku  , target + "-" + fd.format(date) + "-" + "sku");
                     }
                }else {
                    writeToDisk(dateFormat.format(date) + "\t" + numIID + "\t无sku", target + "-" + fd.format(date) + "-" + "sku");
                }
            } else {
                writeToDisk(dateFormat.format(date) + "\t" + numIID , target + "-" + fd.format(date) + "-" + "sku");
            }
        }
    }



    private static TmNumIIDResultBean getDarwinSKU(String target, String numIID) {
        Map<String, Field> fieldMap;
        MultiComplexField darwin_sku = null;
        TmNumIIDResultBean sku = new TmNumIIDResultBean();
        ArrayList<String> SKUList = new ArrayList<>();
        String XMLData = getUpdateItemRule(target, numIID);
        writeSchemaToDisk(XMLData, "updateRule" + numIID, false);
        if (null == XMLData) {
            logIt(fileName, numIID + "\tnoXMLData!");
            return sku;
        }
        try {
            fieldMap = SchemaReader.readXmlForMap(XMLData);
        } catch (TopSchemaException e) {
            return sku;
        }
        Set<String> set = fieldMap.keySet();
        for (String str : set) {
            if (str.equalsIgnoreCase("darwin_sku")) {
                darwin_sku = (MultiComplexField) fieldMap.get("darwin_sku");
            } else if (str.equals("sku")) {
                darwin_sku = (MultiComplexField) fieldMap.get("sku");
            }
        }
        List<ComplexValue> defaultSKUValueList = darwin_sku != null ? darwin_sku.getDefaultComplexValues() : new
                ArrayList<>();

        if (defaultSKUValueList.size() != 0) {
            for (ComplexValue defaultSKUValue : defaultSKUValueList) {
                String sku_outerId = defaultSKUValue.getInputFieldValue("sku_outerId");
                String sku_quantity = defaultSKUValue.getInputFieldValue("sku_quantity");
                String sku_price = defaultSKUValue.getInputFieldValue("sku_price");
                SKUList.add(numIID + "\t" + sku_outerId + "\t" + sku_quantity + "\t" + sku_price);
            }
        } else {
            logIt(fileName, (numIID + "\tnoSkuValue!"));
        }
        sku.getNumIIDList(SKUList);
        return sku;
    }

    private static void logIt(File fileName, String str) {
        try {
            FileWriter outFile = new FileWriter(fileName, true);
            outFile.write(str + "\n");
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // tmall.item.update.schema.get 天猫编辑商品规则获取
    public static String getUpdateItemRule(String target, String numIID) {
        int k = 0;
        String[] appKey = getAppKey(target);

        TmallItemUpdateSchemaGetResponse response;
        String xml_data = "";
        TmallItemUpdateSchemaGetRequest req = new TmallItemUpdateSchemaGetRequest();
        req.setItemId(Long.parseLong(numIID));
        do {
            k++;
            try {
                TaobaoClient client = new DefaultTaobaoClient(URL, appKey[0], appKey[1]);
                response = client.execute(req, appKey[2]);
                xml_data = response.getUpdateItemResult();
                if ("15".equals(response.getErrorCode())) {
                    logIt(fileName, numIID + "\t" + response.getSubMsg());
                    System.out.println(response.getSubMsg());
                } else {
                    writeSchemaToDisk(xml_data, "num_iid", true);
                }
            } catch (ApiException error) {
                error.printStackTrace();
            }
        } while ((null == xml_data || "".equals(xml_data)) && k < 5);
        return xml_data;
    }

    // 将schemaXml写到本地
    public static void writeSchemaToDisk(String str, String cid, boolean isProduct) {
        try {
            if (null == str) {
                str = "";
            }
            Document document = DocumentHelper.parseText(str);
            XMLWriter write;
            if (isProduct) {
                write = new XMLWriter(
                        new FileOutputStream("D:/TmSchema/" + cid + "_product.xml"));
            } else {
                write = new XMLWriter(new FileOutputStream("D:/TmSchema/" + cid + "_item.xml"));
            }
            write.write(document);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeToDisk(String str,String id) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("F:\\TG\\stock\\stock" + id +".txt", true),"UTF-8");
            osw.write(str+ "\n");
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String[] getAppKey(String target) {
        String[] appKey = new String[3];
        if (target.equals("1")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6200a23ce180124c66248fa2bd50420ZZf0df31db94bd5a907029661";
        } else if ("3".equalsIgnoreCase(target)) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6202504c4e05ZZ9b6f8dd389852f9cd35b2cc2d273549c21792368114";
        } else if ("18".equalsIgnoreCase(target)) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6201d2770dbfa1a88af5acfd330fd334fb4ZZa8ff26a40b2641101981";
        } else if (target.equals("7")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6200f0845a29c133ZZe49458c85368465f420a41fae61052260121769";
        }else if (target.equals("17")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "62028196f1c83af1d06d5fbb657ZZ9ba36e68e13c50c1c72587754791";
        }else if (target.equals("16")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6201618b0e82569a52f5a7216fhj3988ec6dcc53580cca52533968112";
        }else if (target.equals("030")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6201f10deedd3c24cbdf464ef0a53abad5eb71a742558302155082130";
        }else if (target.equals("012")) {
            appKey[0] = "21008948";
            appKey[1] = "0a16bd08019790b269322e000e52a19f";
            appKey[2] = "6200430aa76127dd5cacfa356ed5a1bb84b857ZZbf459422694857307";
        }


        return appKey;
    }
 // 更新结果
    private static class TmNumIIDResultBean {
        protected ArrayList<String> numIIDList = new ArrayList<>();
        protected String errMsg;

        public ArrayList<String> getNumIIDList() {
            return numIIDList;
        }

        public void getNumIIDList(ArrayList<String> numIIDList) {
            this.numIIDList = numIIDList;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }
}
