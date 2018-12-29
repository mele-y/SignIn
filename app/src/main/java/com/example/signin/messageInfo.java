package com.example.signin;


public class messageInfo implements Comparable{
    private String stu_name, stu_id, time, sender, content, type;

    public messageInfo(String stu_id, String time,String sender, String content, String type)
    {
        this.stu_id = stu_id;
        this.time = time;
        this.sender = sender;
        this.content=content;
        this.type = type;
        this.stu_name = "";
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getType(){return type;}
    public void setType(String type){this.type=type;}

    @Override
    public int compareTo(Object o) {
        messageInfo s = (messageInfo) o;
        return s.time.compareTo(this.time);
    }
}
