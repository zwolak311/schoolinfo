package com.schoolInfo.bartosz.schoolinfo.Rest;


public class InfoAndToken {
    String token, date, subject, content, id, type;
//    int id;

    public InfoAndToken() {
        super();
    }

    public InfoAndToken(String token, String type, String date, String subject, String content) {
        this.type = type;
        this.token = token;
        this.date = date;
        this.subject = subject;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
