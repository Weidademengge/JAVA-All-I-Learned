package com.duym.usertestall.service.impl;
  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.nio.file.Files;

import com.duym.usertestall.domain.common.ErrorCode;
import com.duym.usertestall.exception.BusinessException;
import com.duym.usertestall.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.stereotype.Service;  
  
@Service("localFileServiceImpl")  
public class LocalFileServiceImpl implements FileService {
  
    private static final Logger logger = LoggerFactory.getLogger(LocalFileServiceImpl.class);  

    //命名空间
    private static final String BUCKET = "ali-course/user-test-all/uploads";
  
    @Override  
    public void upload(InputStream inputStream, String filename) {  
        // 拼接文件上传的路径  
        String path = BUCKET + "/" + filename;  
        // TWR 语法确保了资源会被自动释放。资源的类需要实现 AutoCloseable 接口。  
        try(  
            InputStream innerInputStream = inputStream;  
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));  
        ){  
            // 缓存区拷贝，每次从文件读取1024个字节  
            byte[] buffer = new byte[1024];  
            // 读取文件流长度  
            int length;  
  
            while ((length = innerInputStream.read(buffer)) > 0) {  
                fileOutputStream.write(buffer, 0, length);  
            }  
            fileOutputStream.flush();  
  
        } catch (IOException e) {  
            logger.error("文件上传失败！", e);  
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILURE);
        }  
  
    }  
  
    @Override  
    public void upload(File file) {  
        try {  
            upload(Files.newInputStream(file.toPath()), file.getName());  
        } catch (Exception e) {  
            logger.error("文件上传失败！", e);  
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILURE);  
        }  
    }  
}