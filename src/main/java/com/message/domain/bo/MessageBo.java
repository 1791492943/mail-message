package com.message.domain.bo;

import lombok.Data;

@Data
public class MessageBo {
    /** 接收人 */
    private String[] to;
    /** 主题 */
    private String subject;
    /** 内容 */
    private String content;
    /** cron表达式 */
    private String cron;
}
