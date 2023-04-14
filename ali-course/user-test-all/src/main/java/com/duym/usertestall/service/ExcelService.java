package com.duym.usertestall.service;


import com.duym.usertestall.domain.dto.UserQueryDTO;

public interface ExcelService {
  
    void export(String filename, UserQueryDTO queryDTO);
  
    void asyncExport(String filename, UserQueryDTO queryDTO);  
}