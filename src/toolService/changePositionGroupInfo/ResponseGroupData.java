package toolService.changePositionGroupInfo;

import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/1/19.
 */
public class ResponseGroupData {
    public static void main(String[] args) {
        FileReadWrite.outDataToFile(responseGroupData(FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt")),"转置");
    }

    public static ArrayList<String> responseGroupData (ArrayList<String> dataList){
        ArrayList<String[]> ArrList=new ArrayList<String[]>();
        ArrayList<String> groupList=new ArrayList<String>();


        for(String data:dataList){
            String[] splitData=data.split("\\t");
            ArrList.add(splitData);
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
                    group=group+","+ArrList.get(j)[1];
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
        System.out.println(groupList);
        return groupList;
    }

}
