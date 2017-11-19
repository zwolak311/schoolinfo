package com.schoolInfo.bartosz.schoolinfo.Rest;



public class TokenAndGroupName {
    String token, groupname;


    public TokenAndGroupName() {
    }

    public TokenAndGroupName(String token, String groupname) {
        this.token = token;
        this.groupname = groupname;
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
}
