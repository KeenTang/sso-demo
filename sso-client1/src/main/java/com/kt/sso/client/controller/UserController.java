package com.kt.sso.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-18
 * Time: 22:21
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public Authentication user(Authentication authentication){
        return authentication;
    }
}
