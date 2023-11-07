package com.blog.ui.bloguiservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    private Long id;
    private String body;
    private Date createDate;
    @JsonIgnore
    private Long postId;
    @JsonProperty("post")
    private Post post;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", createDate=" + createDate +
                ", post_id=" + postId +
                '}';
    }
}
