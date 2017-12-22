package toolService.getOrderInfo;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ware.WareReadService.Prop;
import com.jd.open.api.sdk.request.ware.WareReadFindWareByIdRequest;
import com.jd.open.api.sdk.response.ware.WareReadFindWareByIdResponse;
import util.AllUtils;
import util.FileReadWrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by simin on 2017/7/10.
 */
public class GetTradeInfoFromJDG {
    public static final String url_jd = "https://api.jd.com/routerjson";
    public static void main(String[] args) {
        List<String> wareIdList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        String[] appKey = AllUtils.getAppKey("001_26");
        ArrayList<String> wareIdInfo=new ArrayList<>();
        for(String wareId :wareIdList) {
            JdClient client = new DefaultJdClient(url_jd, appKey[2], appKey[0], appKey[1]);
            WareReadFindWareByIdRequest request = new WareReadFindWareByIdRequest();
            request.setWareId(Long.parseLong(wareId));
            request.setField("wareId,props");
            try {
                WareReadFindWareByIdResponse response = client.execute(request);
                Set<Prop> props=response.getWare().getProps();
                for(Prop prop:props){
                    if(prop.getAttrId().equals("86738")){
                        int len=prop.getAttrValueAlias().length;
                        String attrValueAlias="";
                        for(int i=0;i<len;i++){
                            attrValueAlias+=prop.getAttrValueAlias()[i]+"\t";
                        }
                        wareIdInfo.add(wareId+"\t"+attrValueAlias.substring(0,attrValueAlias.lastIndexOf("\t")));
                        System.out.println(wareId+"\t"+attrValueAlias.substring(0,attrValueAlias.lastIndexOf("\t")));
                    }
                }
            } catch (JdException e) {
                e.printStackTrace();
            }
        }
        System.out.println(wareIdInfo);
        FileReadWrite.outDataToFile("F:/JG/wareIdInfo.txt",wareIdInfo);
    }

}
