package com.duym.alinettywebchat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author duym
 * @version $ Id: NettyConfig, v 0.1 2023/04/19 21:26 duym Exp $
 */

@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    private int port;//netty监听的端口
    private String path;//websocket访问路径
}
