package com.duym.nio.c3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author duym
 * @version $ Id: TestFilesCopy, v 0.1 2023/03/23 16:28 duym Exp $
 */
public class TestFilesCopy {

    public static void main(String[] args) throws IOException {
        String source = "...";
        String target = "...";

        Files.walk(Paths.get(source)).forEach(path->{
            try {
                String targetName = path.toString().replace(source,target);
                // 是目录
                if( Files.isDirectory(path)){
                    Files.createDirectory(Paths.get(targetName));
                }
                // 是普通文件
                else if(Files.isRegularFile(path)){
                    Files.copy(path,Paths.get(targetName));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
