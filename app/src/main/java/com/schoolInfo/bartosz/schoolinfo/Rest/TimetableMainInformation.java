package com.schoolInfo.bartosz.schoolinfo.Rest;


import java.util.ArrayList;

public class TimetableMainInformation {

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

    public class Message{
        int id, user_id;
        String name, groupname;
        ArrayList<Days> days = new ArrayList<>();


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

        public ArrayList<Days> getDays() {
            return days;
        }

        public void setDays(ArrayList<Days> days) {
            this.days = days;
        }

        public class Days{
            int id, group_id;
            String name;
            ArrayList<Subject> subjects = new ArrayList<>();


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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<Subject> getSubjects() {
                return subjects;
            }

            public void setSubjects(ArrayList<Subject> subjects) {
                this.subjects = subjects;
            }

            public class Subject{
                int id, group_id;
                String name;
                Pivot pivot = new Pivot();


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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }


                public Pivot getPivot() {
                    return pivot;
                }

                public void setPivot(Pivot pivot) {
                    this.pivot = pivot;
                }

                public class Pivot{
                    int day_id, subject_id, sort;
                    String room;


                    public int getDay_id() {
                        return day_id;
                    }

                    public void setDay_id(int day_id) {
                        this.day_id = day_id;
                    }

                    public int getSubject_id() {
                        return subject_id;
                    }

                    public void setSubject_id(int subject_id) {
                        this.subject_id = subject_id;
                    }

                    public int getSort() {
                        return sort;
                    }

                    public void setSort(int sort) {
                        this.sort = sort;
                    }

                    public String getRoom() {
                        return room;
                    }

                    public void setRoom(String room) {
                        this.room = room;
                    }
                }
            }

        }



    }



}
