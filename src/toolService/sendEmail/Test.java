package toolService.sendEmail;

import util.FileReadWrite;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by simin on 2017/5/24.
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        long size = list.size();
        long t = size / 1000;
        int a=0;
        for (long i = 0; i < t + 1; i++) {
            List<String> testskips = list.stream().skip(i * 1000L).limit(1000L).collect(Collectors.toList());
            System.out.println(testskips);
            a++;
            System.out.println(a);
            int e=(1==2)?1:2;
            System.out.println(e);
        }
    }
}

