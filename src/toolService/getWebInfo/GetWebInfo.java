package toolService.getWebInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * Created by simin on 2017/11/29.
 */
public class GetWebInfo {
    public static void main(String[] args) {

        try {
            String url="https://tmc.tmall.com/campaign/submitBaomingApplyForm.htm?campaignId=18622&baomingConfigId=495287150000";
            java.net.URI uri = new java.net.URI(url);
//            java.awt.Desktop.getDesktop().browse(uri);
            Document doc= Jsoup.connect(url).get();
            System.out.println(doc.body().toString());


        }catch (Exception e){
            System.out.println(e);
        }
    }
}
