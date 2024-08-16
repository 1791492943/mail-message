package com.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol;
    private String defaultEncoding;
    private Properties properties;
}
