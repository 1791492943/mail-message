package com.message.service.impl;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.message.config.MailProperties;
import com.message.domain.entity.MessageEntity;
import com.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.message.utils.MailUtils.messageMap;
import static com.message.utils.TaskUtils.executorService;
import static com.message.utils.TaskUtils.scheduledExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final CronParser cronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));

    @Override
    public void sendMessage(String uuid) {
        MessageEntity message = messageMap.get(uuid);
        if(message == null) return;
        executorService.submit(() -> send(message));
        this.addMessage(message);
    }

    @Override
    public void addMessage(MessageEntity message) {
        AtomicLong delay = new AtomicLong(0);
        Cron cron = cronParser.parse(message.getCron());
        ExecutionTime.forCron(cron).nextExecution(ZonedDateTime.now()).ifPresent((z) -> delay.set(z.toInstant().toEpochMilli() - System.currentTimeMillis()));

        messageMap.putIfAbsent(message.getUuid(),message);
        scheduledExecutorService.schedule(() -> this.sendMessage(message.getUuid()), delay.get(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Collection<MessageEntity> list() {
        return messageMap.values();
    }

    @Override
    public void update(MessageEntity message) {
        messageMap.remove(message.getUuid());
        message.setUuid(UUID.randomUUID().toString());
        messageMap.put(message.getUuid(), message);
        this.addMessage(message);
    }

    @Override
    public void delete(List<String> uuid) {
        uuid.forEach((s) -> messageMap.remove(s));
    }

    private void send(MessageEntity message) {
        switch (message.getStatus()) {
            case 0: return;
            case 2:
                message.setCount(message.getCount() - 1);
                if(message.getCount() == 0) message.setStatus(0);
                messageMap.put(message.getUuid(), message);
                break;
            case 3:
                if(message.getStopTime() < System.currentTimeMillis()) {
                    message.setStatus(0);
                    messageMap.put(message.getUuid(), message);
                    return;
                }
                break;
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.getTo());
        simpleMailMessage.setSubject(message.getSubject());
        simpleMailMessage.setText(message.getContent());
        simpleMailMessage.setFrom(mailProperties.getUsername());
//        javaMailSender.send(simpleMailMessage);
        log.info("邮件信息 {}",message);
    }
}
