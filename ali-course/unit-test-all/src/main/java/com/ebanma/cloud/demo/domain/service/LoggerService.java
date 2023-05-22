package com.ebanma.cloud.demo.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 日志服务类
 */
@Service
public class LoggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    public void saveLog(int code) {
        if (code == 1) {
            LOGGER.info("执行分支1");
            return;
        }
        if (code == 2) {
            LOGGER.info("执行分支2");
            return;
        }
        LOGGER.info("执行默认分支");
    }
}