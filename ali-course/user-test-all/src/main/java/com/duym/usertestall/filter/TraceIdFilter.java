package com.duym.usertestall.filter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @author duym
 * @version $ Id: TraceIdFilter, v 0.1 2023/04/13 17:25 duym Exp $
 */
@WebFilter
public class TraceIdFilter implements Filter {

    private static  final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId = servletRequest.getParameter(TRACE_ID);
        if(StringUtils.isEmpty(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        MDC.put(TRACE_ID,traceId);

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
