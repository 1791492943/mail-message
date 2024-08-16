package com.message.common;

import lombok.Data;

@Data
public class R<T> {

    private T data;
    private String msg;
    private Integer code;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setData(data);
        r.setMsg("操作成功");
        r.setCode(200);
        return r;
    }

    public static R<Void> ok() {
        R<Void> r = new R<>();
        r.setMsg("操作成功");
        r.setCode(200);
        return r;
    }

    public static R<Void> fail(String msg) {
        R<Void> r = new R<>();
        r.setMsg(msg);
        r.setCode(500);
        return r;
    }

}
