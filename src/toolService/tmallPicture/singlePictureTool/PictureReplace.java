package toolService.tmallPicture.singlePictureTool;

import toolService.getFileName.GetFileName;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.PictureReplaceRequest;
import com.taobao.api.response.PictureReplaceResponse;
import util.AllUtils;
import util.FileReadWrite;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by simin on 2017/9/1.
 */
public class PictureReplace {

    private static String target = "005_20";
    protected static String URL = "http://gw.api.taobao.com/router/rest";

    public static void main(String[] args) {
        String[] appKey = AllUtils.getAppKey(target);
        ArrayList<String> picnameList = FileReadWrite.dataList("F:/JM/UpdateDealInfo/Pid&Hid&newPiceList.txt");
        ArrayList<String> listFileName = new ArrayList<String>();
        GetFileName.getAllFileName("F:/TG/replaceImage/image/",listFileName);
        System.out.println(listFileName);
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File("F:/TG/replaceImage/image/"+listFileName.get(0)));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        FileReadWrite.byteToFile("F:/TG/replaceImage/imagebyte.txt",data);
        System.out.println(data);
        TaobaoClient client = new DefaultTaobaoClient(URL, appKey[0], appKey[1]);
        PictureReplaceRequest req = new PictureReplaceRequest();
        req.setPictureId(Long.parseLong(picnameList.get(0)));
        req.setImageData(new FileItem("F:/TG/replaceImage/imagebyte.txt"));
        PictureReplaceResponse rsp =new PictureReplaceResponse();
        try {
            rsp=client.execute(req, appKey[2]);
        } catch (ApiException e) {
            e.printStackTrace();
            System.out.println(picnameList.get(0)+"\ttaobao调用失败");
        }
        if(rsp.getDone() ==true){
            System.out.println(picnameList.get(0)+"\t替换成功");
        }else {
            System.out.println(picnameList.get(0)+"\t替换失败");
        }
    }
}
