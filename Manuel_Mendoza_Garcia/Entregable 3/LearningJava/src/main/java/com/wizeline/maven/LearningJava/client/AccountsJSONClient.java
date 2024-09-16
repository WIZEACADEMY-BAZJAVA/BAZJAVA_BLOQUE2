package com.wizeline.maven.LearningJava.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wizeline.maven.LearningJava.model.Post;


@FeignClient(value="getAccountsClient", url="https://jsonplaceholder.typicode.com/")
public interface AccountsJSONClient {
    @GetMapping(value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);
}