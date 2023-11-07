package com.blog.ui.bloguiservice.controller;

import com.blog.ui.bloguiservice.model.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    @Value("${blog.service.url}")
    private String blogService;
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/newPost")
    public String newPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);

        return "/postForm";
    }

    @PostMapping(value = "/newPost")
    public String createNewPost(@Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/postForm";
        } else {
            restTemplate.postForEntity(blogService + "/posts", post, Void.class);
            return "redirect:/blog";
        }
    }

    @GetMapping(value = "/editPost/{id}")
    public String editPostWithId(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = getForObject(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "/postForm";
        } else {
            return "/error";
        }
    }

    @GetMapping(value = "/post/{id}")
    public String getPostWithId(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = getForObject(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "/post";
        } else {
            return "/error";
        }
    }

    @DeleteMapping(value = "/post/{id}")
    public String deletePostWithId(@PathVariable Long id) {
        Optional<Post> optionalPost = getForObject(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            restTemplate.delete(blogService + "/posts/" + post.getId());
            return "redirect:/home";
        } else {
            return "/error";
        }
    }

    private Optional<Post> getForObject(Long id) {
        return Optional.ofNullable(restTemplate.getForObject(blogService + "/posts/" + id, Post.class));
    }
}
