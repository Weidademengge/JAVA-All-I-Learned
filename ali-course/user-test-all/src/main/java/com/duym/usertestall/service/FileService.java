package com.duym.usertestall.service;
  
import java.io.File;  
import java.io.InputStream;  
  
public interface FileService {  
  
    void upload(InputStream inputStream, String filename);  
  
    void upload(File file);  
}