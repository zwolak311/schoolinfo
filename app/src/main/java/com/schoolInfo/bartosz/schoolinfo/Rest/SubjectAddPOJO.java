package com.schoolInfo.bartosz.schoolinfo.Rest;


public class SubjectAddPOJO {

    String groupname, subject, token;

    public SubjectAddPOJO() {
    }

    public SubjectAddPOJO(String groupname, String subject, String token) {
        this.groupname = groupname;
        this.subject = subject;
        this.token = token;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
