package com.schoolInfo.bartosz.schoolinfo.Rest;


public class SubjectRemovePOJO {

    String groupname, token;
    int subject;


    public SubjectRemovePOJO() {
    }

    public SubjectRemovePOJO(String groupname, String token, int subject) {
        this.groupname = groupname;
        this.token = token;
        this.subject = subject;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
