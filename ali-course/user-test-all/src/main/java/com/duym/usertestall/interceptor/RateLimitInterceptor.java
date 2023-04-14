package com.duym.usertestall.interceptor;

import com.duym.usertestall.domain.common.ErrorCode;
import com.duym.usertestall.exception.BusinessException;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author duym
 * @version $ Id: RateLimitInterceptor, v 0.1 2023/04/13 17:00 duym Exp $
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {


    /**
     * 速度限制器(QPS限制为1,1秒钟请求一次)
     */
    private static final RateLimiter rateLimiter = RateLimiter.create(1);

    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(rateLimiter.tryAcquire()){
            logger.error("系统已经被限流了");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return true;
    }
}
