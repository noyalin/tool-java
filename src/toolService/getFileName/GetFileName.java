package toolService.getFileName;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by simin on 2017/9/1.
 */
public class GetFileName {
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }
    public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
            fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
    public static void main(String[] args)
    {
        String [] fileName = getFileName("F:\\xiaoshuo");
        for(String name:fileName)
        {
            System.out.println(name);
        }
        System.out.println("--------------------------------");
        ArrayList<String> listFileName = new ArrayList<String>();
        getAllFileName("F:\\xiaoshuo",listFileName);
        for(String name:listFileName)
        {
            System.out.println(name);
        }

    }
}
