package toolService.changePositionGroupInfo;

import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/1/18.
 */
public class GroupImage {

    private static final String IMG_HTML = "<img src=\"%s\" alt=\"\" />";
    private static final String IMG_6_1="http://p12.jmstatic.com/open_api/gPop_131/001/channel/SN6_1.jpeg";
    private static final String IMG_6_2="http://p12.jmstatic.com/open_api/gPop_131/001/channel/SN6_2.jpeg";
    private static final StringBuffer IMG_SHOP=new StringBuffer();
    public static void main(String[] args) {
        FileReadWrite.outDataToFile(responseGroupData(FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt")),"转置");
    }

    public static  ArrayList<String> responseGroupData (ArrayList<String> dataList){
        ArrayList<String[]> ArrList=new ArrayList<String[]>();
        ArrayList<String> groupList=new ArrayList<String>();
//        StringBuffer IMG_SHOP= new StringBuffer();
        IMG_SHOP.append(String.format(IMG_HTML,IMG_6_1));
        IMG_SHOP.append(String.format(IMG_HTML,IMG_6_2));


        for(String data:dataList){
            String[] splitData=data.split("\\t");
            ArrList.add(splitData);
        }
        for(int i=0;i<ArrList.size();i++){
            StringBuffer stringBuffer = new StringBuffer();
            String group=ArrList.get(i)[1];
            StringBuffer img =stringBuffer.append(String.format(IMG_HTML,group));
            int interval=0;
            if(i==ArrList.size()-1){
                group=ArrList.get(i)[0]+"\t"+img.append(IMG_SHOP);
                groupList.add(group);
                break;
            }
            for(int j=i+1;j<=ArrList.size();j++){
                if(ArrList.get(i)[0].equals(ArrList.get(j)[0])){
                    img=stringBuffer.append(String.format(IMG_HTML,ArrList.get(j)[1]));
                    interval=interval+1;
                    if(j==ArrList.size()-1){
                        group=ArrList.get(i)[0]+"\t"+img.append(IMG_SHOP);
                        groupList.add(group);
                        i=i+interval;
                        break;
                    }
                }else if((!ArrList.get(i)[0].equals(ArrList.get(j)[0]))&&i!=(ArrList.size()-1)){
                    group=ArrList.get(i)[0]+"\t"+img.append(IMG_SHOP);
//                    System.out.println(JacksonUtil);
                    groupList.add(group);
                    i=i+interval;
                    break;
                }
            }
        }

        System.out.println(groupList);
        return groupList;
    }


}
