package com.blog.ui.bloguiservice.controller;

import com.blog.ui.bloguiservice.model.Post;
import com.blog.ui.bloguiservice.util.CustomPageImpl;
import com.blog.ui.bloguiservice.util.Pager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BlogController {
    @Value("${blog.service.url}")
    private String blogService;
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/blog")
    public String blogForUsername(@RequestParam(defaultValue = "0") int page,
                                  Model model) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(blogService + "/posts")
                .queryParam("page", page);

        ResponseEntity<CustomPageImpl<Post>> exchange = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<Post>>() {
        });
        Pager pager = new Pager(exchange.getBody());

        model.addAttribute("pager", pager);

        return "/posts";
    }
}
