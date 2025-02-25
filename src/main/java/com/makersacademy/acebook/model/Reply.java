package com.makersacademy.acebook.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
@Table(name = "REPLIES")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String time_posted;
    private int post_id;
    private Integer user_id;
    private String username;

    public Reply() {}

    public Reply(String content) {
        this.content = content;
    }
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }

    public String getTime_posted() { return this.time_posted; }
    public void setTime_posted(String time_posted) { this.time_posted = time_posted; }

    public int getPost_id() { return this.post_id; }
    public void setPost_id(int post_id) { this.post_id = post_id; }

    public Integer getUser_id() { return this.user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

}
