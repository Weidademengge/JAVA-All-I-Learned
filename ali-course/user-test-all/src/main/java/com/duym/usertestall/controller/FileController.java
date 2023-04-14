package com.duym.usertestall.controller;
  
import java.io.IOException;  
  
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.duym.usertestall.domain.common.ErrorCode;
import com.duym.usertestall.domain.common.Result;
import com.duym.usertestall.domain.dto.UserQueryDTO;
import com.duym.usertestall.exception.BusinessException;
import com.duym.usertestall.service.ExcelService;
import com.duym.usertestall.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.multipart.MultipartFile;  
  
@RestController  
@RequestMapping("/api/file")  
public class FileController {  
  
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);  
  
    @Resource(name = "localFileServiceImpl")  
    private FileService fileService;
  
    @RequestMapping("/upload")  
    public Result<String> upload(@NotNull MultipartFile file) {  
  
        try {  
            fileService.upload(file.getInputStream(), file.getOriginalFilename());  
        } catch (IOException e) {  
            logger.error("文件上传失败！", e);  
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILURE, e);
        }  
  
        return Result.success(file.getOriginalFilename());
    }

    @Autowired
    private ExcelService excelService;

    /**
     * GET * 用户数据导出
     */
    @GetMapping("/export")
    public Result<Boolean> export(@NotEmpty String filename, UserQueryDTO queryDTO) {

        excelService.export(filename, queryDTO);

        return Result.success(Boolean.TRUE);
    }
}