package toolService.downLoadPicture.threadDownLoad;

import util.FileReadWrite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by simin on 2017/1/18.
 */
public class RunLoadImage {

    private static final int MAX_THREAD=200;
    private static final String PATH="E:/image/images/";

    public static void main(String[] args) {
        int threadCount;
        ArrayList<String> urlList=FileReadWrite.dataList("E:/image/Url.txt");
        File file=new File(PATH);
        if (file.isDirectory()){//判断file是否是文件目录 若是返回TRUE
            String name[]=file.list();//name存储file文件夹中的文件名
            for (int i=0; i<name.length; i++){
                File f=new File(PATH, name[i]);//此时就可得到文件夹中的文件
                f.delete();//删除文件
            }
        }
        if(urlList.size()>MAX_THREAD){
            threadCount=MAX_THREAD;
        }else {
            threadCount=urlList.size();
        }
        int group=urlList.size()/threadCount;
        int remainder=0;
        for (int i=0;i<threadCount;i++){

            List<String> taskUrlList=urlList.subList(i*group,(i+1)*group);
            DownLoadImage downLoadImage=new DownLoadImage(taskUrlList,i+"号",PATH);
            downLoadImage.start();
            if(i==threadCount-1 && urlList.size()>MAX_THREAD){
                int rei=urlList.size()%threadCount;
                List<String> reiList=urlList.subList((i+1)*group,(i+1)*group+rei);
                for(int j=0;j<reiList.size();j++){
                    List<String> reiUrlList=reiList.subList(j,j+1);
                    DownLoadImage reidownLoadImage=new DownLoadImage(reiUrlList,(i+j)+"号",PATH);
                    reidownLoadImage.start();
                }
            }
        }
    }


}
