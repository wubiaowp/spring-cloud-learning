package com.test.author.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 测试通用接口
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get")
    public Principal user(Principal principal){
        return principal;
    }

    @GetMapping("/{name}")
    public String getUserName(@PathVariable String name){
        return "返回结果为:"+name;
    }

}
