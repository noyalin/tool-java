package toolService.downLoadPicture.threadDownLoad;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by simin on 2017/1/18.
 */
public class DownLoadImage implements Runnable{

    private List<String> imageList;
    private String dowanLoadThreadName;
    private Thread thread;
    private String PATH;
    //DEFAULT
    private static final String URI="http://s7d5.scene7.com/is/image/sneakerhead/jm-20170331-790-604?$790-604$&$img1=";
    //RM
    private static final String RM_URI="http://s7d5.scene7.com/is/image/sneakerhead/REALMADRID20170615TEST1?$KITBAG20170421790x750TEST1$&$PRODUCT=";
    //spalding主图
    private static final String mainURI="http://s7d5.scene7.com/is/image/sneakerhead/new_bigball_body?$pa_1200x1200$&$imagemoban=";
    //spalding透明素材图
    private static final String SP_800X800_URI="http://s7d5.scene7.com/is/image/sneakerhead/spalding800x800?$800_800_new_test_file$&$imagemoban=";
    //RM 800*800
    private static final String RM_800x800_URI="http://s7d5.scene7.com/is/image/sneakerhead/CPRMKTTONGYONGMUBAN800X8000724?$champion20170314800x800baoyou1$&$product=";
    //MC 800*800
    private static  final String MC_800x800_URI="http://s7d5.scene7.com/is/image/sneakerhead/CPRMKTTONGYONGMUBAN800X8000724?$champion20170314800x800baoyou1$&$product=";
    //白底图
    private static  final String SP_BAIDITU_URI="http://s7d5.scene7.com/is/image/sneakerhead/spalding_400x600?$400x600$&$imagemoban=";


    public DownLoadImage(List<String> imageList, String dowanLoadThreadName,String path){
        this.imageList=imageList;
        this.dowanLoadThreadName=dowanLoadThreadName;
        this.PATH=path;
    }
    public void  run(){
        int imageNumber = 1;
        System.out.println(dowanLoadThreadName+"线程开启，准备开始下载图片！");
        for (String urlString : imageList) {
            try {
                URL url = new URL(SP_800X800_URI+urlString.substring(0, urlString.lastIndexOf('\t')));
//                URL url = new URL(urlString.substring(0, urlString.lastIndexOf('\t')));
                String image_key=urlString.substring(urlString.lastIndexOf('\t') + 1);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//    			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.59", 7128));
//    			URLConnection conn = url.openConnection(proxy);
//    			InputStream br = conn.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(PATH+image_key+".jpg"));
                byte[] buffer = new byte[8096];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.write(buffer);
                fileOutputStream.flush();
                fileOutputStream.close();
                System.out.println(dowanLoadThreadName+"的第"+imageNumber+"张->"+"下好了！");
                imageNumber++;
                try {
                    thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println(dowanLoadThreadName+"的第"+imageNumber+"张->"+"失败了！");
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(dowanLoadThreadName+"下完啦！");
    }
    public  void  start() {
        if (thread == null) {
            thread = new Thread(this, dowanLoadThreadName);
            thread.start();
        }
    }
}
