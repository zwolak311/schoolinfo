package com.schoolInfo.bartosz.schoolinfo.Rest;


public class TimetableField {


    String groupname, room, token;
    int day, subject, sort;


    public TimetableField() {
    }

    public TimetableField(String token, String groupname, int subject, int day, int sort) {
        this.groupname = groupname;
        this.day = day;
        this.subject = subject;
        this.sort = sort;
        this.token = token;
    }

    public TimetableField(String token, String groupname, int subject, int day, int sort, String room) {
        this.groupname = groupname;
        this.room = room;
        this.day = day;
        this.subject = subject;
        this.sort = sort;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
