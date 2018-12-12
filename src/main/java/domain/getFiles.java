package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
    遍历文件目录
 */
public class getFiles {

    public static  List<String> getFileList(String fileDir) {

        List<String> fileList = new ArrayList<String>();
        File file = new File(fileDir);
        File[] files = file.listFiles();

        if (files == null) {
            System.out.println("目录为空");
        }
        // 递归遍历
        for (File f : files) {

            if (f.isFile()) {

                fileList.add(f.getPath());

            } else if (f.isDirectory()) {

                getFileList(f.getAbsolutePath());

            }
        }

        return fileList;

    }

}
