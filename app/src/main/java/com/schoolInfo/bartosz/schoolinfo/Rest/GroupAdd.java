package com.schoolInfo.bartosz.schoolinfo.Rest;


public class GroupAdd {

    String token, name, groupname;

    public GroupAdd() {
    }

    public GroupAdd(String token, String name, String groupname) {
        this.token = token;
        this.name = name;
        this.groupname = groupname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
