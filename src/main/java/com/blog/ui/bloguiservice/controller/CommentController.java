package com.blog.ui.bloguiservice.controller;

import com.blog.ui.bloguiservice.model.Comment;
import com.blog.ui.bloguiservice.model.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {
    @Value("${blog.service.url}")
    private String blogService;
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value = "/createComment")
    public String createNewPost(@Valid Comment comment,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/commentForm";

        } else {
            Optional<Post> optPost = Optional.ofNullable(restTemplate.getForObject(blogService + "/posts/" + comment.getPostId(), Post.class));
            comment.setPost(optPost.get());

            restTemplate.postForEntity(blogService + "/comments", comment, Void.class);
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

    @GetMapping(value = "/commentPost/{id}")
    public String commentPostWithId(@PathVariable Long id, Model model) {
        Optional<Post> optPost = Optional.ofNullable(restTemplate.getForObject(blogService + "/posts/" + id, Post.class));

        if (optPost.isPresent()) {
            Comment comment = new Comment();
            comment.setPostId(optPost.get().getId());
            model.addAttribute("comment", comment);
            return "/commentForm";
        } else {
            return "/error";
        }
    }
}
