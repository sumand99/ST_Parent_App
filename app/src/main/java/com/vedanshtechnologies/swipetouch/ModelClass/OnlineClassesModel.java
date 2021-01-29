package com.swipetouch.ModelClass;

public class OnlineClassesModel {
    public String class_Start_date;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClass_start_date_time() {
        return class_start_date_time;
    }

    public void setClass_start_date_time(String class_start_date_time) {
        this.class_start_date_time = class_start_date_time;
    }

    public String getClass_end_date_time() {
        return class_end_date_time;
    }

    public void setClass_end_date_time(String class_end_date_time) {
        this.class_end_date_time = class_end_date_time;
    }

    public  String topic;
    public  String description;
    public  String subject;
    public  String teacher;
    public  String class_start_date_time;
    public  String class_end_date_time;

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public  String is_blocked;

    public String getMeeting_URL() {
        return Meeting_URL;
    }

    public void setMeeting_URL(String meeting_URL) {
        Meeting_URL = meeting_URL;
    }

    public  String Meeting_URL;
}
