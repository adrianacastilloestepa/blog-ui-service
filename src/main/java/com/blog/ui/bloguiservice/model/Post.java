package com.blog.ui.bloguiservice.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
public class Post  implements Serializable {
    private Long id;
    private String title;
    private String body;
    private Date createDate;
    private Collection<Comment> comments;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
