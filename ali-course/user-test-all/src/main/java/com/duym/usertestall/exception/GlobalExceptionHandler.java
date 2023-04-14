package com.duym.usertestall.exception;
  
import com.duym.usertestall.domain.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.web.bind.annotation.ControllerAdvice;  
import org.springframework.web.bind.annotation.ExceptionHandler;  
import org.springframework.web.bind.annotation.ResponseBody;  
  
@ControllerAdvice  
public class GlobalExceptionHandler {  
  
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);  
  
    @ResponseBody  
    @ExceptionHandler(value = BusinessException.class)  
    public Result businessExceptionHandler(BusinessException e) {
        logger.error("捕捉到业务异常：", e);  
        return Result.fail(e);  
    }  
  
    @ResponseBody  
    @ExceptionHandler(value = RuntimeException.class)  
    public Result runtimeExceptionHandle(RuntimeException e) {  
        logger.error("捕捉到运行时异常：", e);  
        return Result.fail(e);  
    }  
  
    @ResponseBody  
    @ExceptionHandler(value = Throwable.class)  
    public Result throwableHandle(Throwable th) {  
        logger.error("捕捉Throwable异常：", th);  
        return Result.fail(th);  
    }  
}