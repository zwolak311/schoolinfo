package com.schoolInfo.bartosz.schoolinfo.Rest;

import java.util.ArrayList;

public class UserBaseInformation {
    String name, username, email;
    int id;

    User user = new User();
    ArrayList<Group> groups = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Group> getGroups() {

//        groups.add(new Group());

        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }


    public UserBaseInformation() {

        user = new User();
        groups = new ArrayList<>();
    }


    public void setClassesSize(int id){

        for (int i = 0; i < id; i++) {
            groups.add(new Group());
        }

    }

    public class User {
        String id, name, username, email;

        public User() {
        }

        public User(String id, String name, String username, String email) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public class Group {
        int id, user_id;
        String name, groupname;

        public Group() {
        }

        public Group(int id, int user_id, String name, String groupname) {
            this.id = id;
            this.user_id = user_id;
            this.name = name;
            this.groupname = groupname;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

}
