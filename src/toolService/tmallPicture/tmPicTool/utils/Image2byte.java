package toolService.tmallPicture.tmPicTool.utils;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by simin on 2017/9/1.
 */
public class Image2byte {
    public byte[] imageTobyte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
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
        return data;
    }

    public static byte[] getImageFromNetByUrl(String strUrl){
//        String str=strUrl.replaceAll(" ","%20");
        try {
            URL url = new URL(strUrl );
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream=null;
            try {
                inStream = conn.getInputStream();//通过输入流获取图片数据
            }catch (IOException e){
                return null;
            }
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static String urlEncode(String[] picUrlInfoArr,String urlMoban_Detail) {
        String urlMoban = "";
        try {
            urlMoban = urlMoban_Detail
                    .replace("{brand}", URLEncoder.encode(picUrlInfoArr[5], "utf-8"))
                    .replace("{sex}", URLEncoder.encode(picUrlInfoArr[6], "utf-8"))
                    .replace("{capacity}", URLEncoder.encode(picUrlInfoArr[7], "utf-8"))
                    .replace("{fragrance}", URLEncoder.encode(picUrlInfoArr[8], "utf-8"))
                    .replace("{topNotes}", URLEncoder.encode(picUrlInfoArr[9], "utf-8"))
                    .replace("{middleNotes}", URLEncoder.encode(picUrlInfoArr[10], "utf-8"))
                    .replace("{afterNotes}", URLEncoder.encode(picUrlInfoArr[11], "utf-8"))
                    .replace("{description}", URLEncoder.encode(picUrlInfoArr[12], "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlMoban;
    };
}
