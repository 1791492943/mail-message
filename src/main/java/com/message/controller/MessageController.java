package com.message.controller;

import com.message.common.R;
import com.message.domain.entity.MessageEntity;
import com.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * 添加消息
     */
    @PostMapping
    public R<Void> addMessage(@RequestBody MessageEntity messageEntity) {
        messageService.addMessage(messageEntity);
        return R.ok();
    }

    /**
     * 查询全部任务
     */
    @GetMapping("/list")
    public R<Collection<MessageEntity>> list() {
        Collection<MessageEntity> list = messageService.list();
        return R.ok(list);
    }

    /**
     * 更新消息
     */
    @PutMapping
    public R<Void> update(@RequestBody MessageEntity message) {
        messageService.update(message);
        return R.ok();
    }

}
