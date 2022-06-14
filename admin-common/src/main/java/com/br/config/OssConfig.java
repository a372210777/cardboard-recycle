package com.br.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * oss配置
 * @Author Linrl
 * @Date 2022/1/7 23:33
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
@ConditionalOnProperty(name = "oss.enable", havingValue="true")
public class OssConfig {
    private boolean enable;
    private String protocol;
    private String key;
    private String secret;
    private String endpoint;
    private String defimgbucket;
    private String defbucket;
    private String imagedomain = "";
}
