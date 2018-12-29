package com.example.signin;

public class noticeInfo implements Comparable{
    private String title, content, time;

    public noticeInfo(String title,String content,String time)
    {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        noticeInfo s = (noticeInfo) o;
        return s.time.compareTo(this.time);
    }
}
