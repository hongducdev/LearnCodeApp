package com.example.learncodeapp;

public class CommentModel {
    String name, comment, timestamp, username;

    public CommentModel(String name, String comment, String timestamp, String username) {
        this.name = name;
        this.comment = comment;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
