package com.visabao.machine.entity;

import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;

/***
 * //发票信息
 */
public class Notes implements Serializable {
    private  boolean is_notes =false;  //是否需要发票
    private  String rise ="";   //发票抬头
    private  String content ="";  //发票内容
    private  String notestype ="";//发票类型

    public boolean is_notes() {
        return is_notes;
    }

    public void setIs_notes(boolean is_notes) {
        this.is_notes = is_notes;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotestype() {
        return notestype;
    }

    public void setNotestype(String notestype) {
        this.notestype = notestype;
    }


}
