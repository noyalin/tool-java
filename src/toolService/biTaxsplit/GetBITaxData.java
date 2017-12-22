package toolService.biTaxsplit;

import util.FileReadWrite;

import java.util.ArrayList;

/**
 * Created by simin on 2017/3/1.
 */
public class GetBITaxData {
    public static void main(String[] args) {
        ArrayList<String> data_List=FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        FileReadWrite.outDataToFile("F:/财务报表/Tax.txt",getTaxData(data_List));
    }
    public static ArrayList<String>  getTaxData(ArrayList<String> data_List){
//        String []arr=null;
        ArrayList<String> strList=new ArrayList<>();
        for(String data:data_List){
            String []arr = data.split("\\t");
            String mainTrack_no=arr[0].substring(0,3)+"-"+arr[0].substring(3);
            strList.add(mainTrack_no+"\t"+arr[2]+"\t"+arr[1]+"\t"+arr[10]);
        }
        return strList;
    }
}
