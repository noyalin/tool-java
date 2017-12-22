package util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by simin on 2017/1/18.
 */
public class FileReadWrite {
    /**
     * 读取本地txt
     * @return
     */
    public static ArrayList<String> dataList( String name){
        ArrayList<String> data_List =new ArrayList<String>();
        try {
            Scanner in = new Scanner(new File(name));
            while (in.hasNextLine()) {
                String str = in.nextLine();
                data_List.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("读取数据完毕，共"+data_List.size()+"条！");
        return data_List;
    }

    /**
     *取得的数据Push到文本中
     * @param outlist
     * @param filename
     */
    public static  void  outDataToFile(ArrayList<String> outlist,String filename){
        try {
            File file=new File("F:/JM/UpdateDealInfo/各种聚美数据/"+filename+".txt");
            BufferedWriter bf=new BufferedWriter(new PrintWriter(file));
            for (String out:outlist){
                bf.write(out);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *取得的数据Push到文本中
     * @param outlist
     * @param path
     */
    public static  void  outDataToFile(String path,ArrayList<String> outlist){
        try {
            File file=new File(path);
            BufferedWriter bf=new BufferedWriter(new PrintWriter(file));
            for (String out:outlist){
                bf.write(out);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  void  saveSchame(String schema,String proudct_id,boolean isProduct){
        try {
            if (null == schema) {
                schema = "";
            }
            Document document = DocumentHelper.parseText(schema);
            XMLWriter write;
            if (isProduct) {
                write = new XMLWriter(
                        new FileOutputStream("F:/TG/schema/" + proudct_id + "_product.xml"));
            } else {
                write = new XMLWriter(new FileOutputStream("F:/TG/schema/" + proudct_id + "_item.xml"));
            }
            write.write(document);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static  void  byteToFile(String path,byte[] data){
        File outputFile = new File(path);
        FileOutputStream outputFileStream = null;
        try {
            outputFileStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputFileStream.write(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            outputFileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
