package toolService.downLoadPicture.old;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import java.io.*;
import java.lang.*;


/**
 * Created by simin on 2017/1/18.
 */
public class DownLoadImage_old {
    public static void main(String[] args){
        downloadimage(readUrlList());
    }
    public static void downloadimage(ArrayList<String> urlList){
        URL url = null;
        int imageNumber = 1;
        String filePath="E:/image";
        System.out.println("开始通过URL下图片！");
        for (String urlString : urlList) {
            try {
                url = new URL(urlString.substring(0, urlString.lastIndexOf('\t')));

                String image_key=urlString.substring(urlString.lastIndexOf('\t') + 1);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//    			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.59", 7128));
//    			URLConnection conn = url.openConnection(proxy);
//    			InputStream br = conn.getInputStream();


                FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/image/sn/"+image_key+".jpg"));
                System.out.println("第"+imageNumber+"张->"+"下好了！");
                imageNumber++;
                byte[] buffer = new byte[8096];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.write(buffer);
                fileOutputStream.flush();
                fileOutputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
        }
        System.out.println("下完啦！");
        Date getDate1=new Date();
        System.out.println(getDate1);
    }

    private static ArrayList<String> readUrlList(){
        ArrayList<String> urlList =new ArrayList();
        try {
            Scanner in = new Scanner(new File("E:/Url.txt"));
            while (in.hasNextLine()) {
                String str = in.nextLine();
                urlList.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("读取URL数据完毕，共"+urlList.size()+"条！");
        return urlList;

    }
    private  String getUtf8Url(String url) {
        char[] chars = url.toCharArray();
        StringBuilder utf8Url = new StringBuilder();
        final int charCount = chars.length;
        for (int i = 0; i < charCount; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 1 && (chars[i] !=' ')) {
                utf8Url.append(chars[i]);
            }else{
                try {
                    if (' ' == chars[i]) {
                        System.err.println(chars[i]);
                    }
                    utf8Url.append(URLEncoder.encode(String.valueOf(chars[i]), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return utf8Url.toString();
    }
}
