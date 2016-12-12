package com.visabao.machine.global;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class MessageResults<E> implements Serializable {
    private boolean success;
    private String msg;
    private E data;

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public E getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(E data) {
        this.data = data;
    }

    public MessageResults<E> pasreJsonResults(String json) {
        MessageResults<E> messageResults = null;
        try {
            messageResults = new Gson().fromJson(json, new TypeToken<MessageResults<E>>() {
            }.getType());
        } catch (Exception e) {
            messageResults = new MessageResults();
            messageResults.setSuccess(false);
            messageResults.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return messageResults;
    }


}
