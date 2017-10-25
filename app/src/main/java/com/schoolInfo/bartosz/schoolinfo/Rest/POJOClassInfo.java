package com.schoolInfo.bartosz.schoolinfo.Rest;


import java.util.ArrayList;

public class POJOClassInfo {
    String groupname, status;
    Owner owner;
    ArrayList<Members> members = new ArrayList<>();
    ArrayList<Requests> requests = new ArrayList<>();
    ArrayList<Information> informations = new ArrayList<>();
    ArrayList<Exam> exams = new ArrayList<>();
    Plan plan;
    User user;

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Requests> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Requests> requests) {
        this.requests = requests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ArrayList<Members> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Members> members) {
        this.members = members;
    }

    public ArrayList<Information> getInformations() {
        return informations;
    }

    public void setInformations(ArrayList<Information> informations) {
        this.informations = informations;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }



    public class Owner {
        String id, name, username, email;

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


    public class User {
        int id, group_id, priority, manage_info, manage_data, manage_members, manage_timetable, manage_roles;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getManage_info() {
            return manage_info;
        }

        public void setManage_info(int manage_info) {
            this.manage_info = manage_info;
        }

        public int getManage_data() {
            return manage_data;
        }

        public void setManage_data(int manage_data) {
            this.manage_data = manage_data;
        }

        public int getManage_members() {
            return manage_members;
        }

        public void setManage_members(int manage_members) {
            this.manage_members = manage_members;
        }

        public int getManage_timetable() {
            return manage_timetable;
        }

        public void setManage_timetable(int manage_timetable) {
            this.manage_timetable = manage_timetable;
        }

        public int getManage_roles() {
            return manage_roles;
        }

        public void setManage_roles(int manage_roles) {
            this.manage_roles = manage_roles;
        }
    }

    public class Requests {

        int id, user_id;
        Members user;


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

        public Members getUser() {
            return user;
        }

        public void setUser(Members user) {
            this.user = user;
        }
    }

    public class Members {

//        public Members(Pivot pivot) {
//            this.pivot = pivot;
//        }

        String  name, username, email;
        Pivot pivot;
        int id;


        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }

        public class Pivot{
            String group_id, user_id;

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }

    }

    public class Information {
        String group_id, date, subject, content, type;
        int accepted, visibility, id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
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


        public int getAccepted() {
            return accepted;
        }

        public void setAccepted(int accepted) {
            this.accepted = accepted;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class Exam {
        String group_id, date, subject, content;
        int accepted, visibility, id;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
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

        public int getAccepted() {
            return accepted;
        }

        public void setAccepted(int accepted) {
            this.accepted = accepted;
        }

        public int getVisibility() {
            return visibility;
        }

        public void setVisibility(int visibility) {
            this.visibility = visibility;
        }
    }

    public class Plan {

    }

}
