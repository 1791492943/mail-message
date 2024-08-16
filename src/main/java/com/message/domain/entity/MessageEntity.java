package com.message.domain.entity;

import lombok.Data;

import java.util.UUID;

/**
 * 消息对象
 */
@Data
public class MessageEntity {
    /** 消息唯一标识 */
    private String uuid = UUID.randomUUID().toString();
    /** 收件人邮箱 */
    private String[] to;
    /** 邮件主题 */
    private String subject;
    /** 邮件内容 */
    private String content;
    /** cron表达式 */
    private String cron;
    /** 状态(0:停止 1:正常 2:计次 3:计时) */
    private Integer status = 1;
    /** 剩余次数 */
    private Integer count;
    /** 停止时间 */
    private Long stopTime;
}
