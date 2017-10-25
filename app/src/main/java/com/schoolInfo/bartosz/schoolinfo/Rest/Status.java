package com.schoolInfo.bartosz.schoolinfo.Rest;


import java.util.ArrayList;

public class Status {

    public boolean status;

    public ArrayList<Message> message = new ArrayList<>();

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public ArrayList<Message> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Message> message) {
        this.message = message;
    }

    public class Message {

        int id, user_id;
        String name, groupname;


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
