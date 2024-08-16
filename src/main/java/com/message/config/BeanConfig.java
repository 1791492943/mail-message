package com.message.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class BeanConfig {

    @Autowired
    private MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperties.getHost());
        javaMailSender.setUsername(mailProperties.getUsername());
        javaMailSender.setPassword(mailProperties.getPassword());
        javaMailSender.setPort(mailProperties.getPort());
        javaMailSender.setDefaultEncoding(mailProperties.getDefaultEncoding());
        javaMailSender.setProtocol(mailProperties.getProtocol());
        javaMailSender.setJavaMailProperties(mailProperties.getProperties());
        return javaMailSender;
    }

}
