package com.schoolInfo.bartosz.schoolinfo.Rest;


import com.schoolInfo.bartosz.schoolinfo.MainActivity.MainActivity;

import java.util.ArrayList;

public class Requests {

    boolean status;
    ArrayList<Message> message = new ArrayList<>();


    public boolean isStatus() {
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

        int id, user_id, requestable_id;
        String requestable_type;
        Group group;

        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
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

        public int getRequestable_id() {
            return requestable_id;
        }

        public void setRequestable_id(int requestable_id) {
            this.requestable_id = requestable_id;
        }

        public String getRequestable_type() {
            return requestable_type;
        }

        public void setRequestable_type(String requestable_type) {
            this.requestable_type = requestable_type;
        }

        public class Group {

            int id, user_id, requestable_type;
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

            public int getRequestable_type() {
                return requestable_type;
            }

            public void setRequestable_type(int requestable_type) {
                this.requestable_type = requestable_type;
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


}
