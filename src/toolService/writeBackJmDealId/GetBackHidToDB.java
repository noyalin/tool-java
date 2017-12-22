package toolService.writeBackJmDealId;

import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/4/11.
 */
public class GetBackHidToDB {
    public static void main(String[] args) {
        //回写一个promotion里所有的code
        //https://cms.voyageone.net/cms/backdoor/updateJmPromotionMallIdByPromotionId?channelId=001&pId=1860&code=
        ArrayList<String> channel_code= FileReadWrite.dataList("F:/JM/UpdateDealInfo/GetBcakHID.txt");
        for(String chcode:channel_code){
            String url="";
            String[] a=chcode.split("\\t");
//            url="https://cms.voyageone.net/cms/backdoor/updateProductJMHashId?channelId="+a[0]+"&productCode="+a[1]+"&flg=1";
            url="http://cms.voyageone.net/cms/backdoor/updateJmPromotionMallIdByPromotionId?channelId="+a[0]+"&pId="+ a[1]+"&code="+a[2].replace(" ","%20");

            try {
//                URLConnection conn = new URL(url).openConnection();
                java.net.URI uri = new java.net.URI(url);
                java.awt.Desktop.getDesktop().browse(uri);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
