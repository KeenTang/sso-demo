package com.kt.sso.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-29
 * Time: 23:18
 */
@RestController
public class UserController {
    @GetMapping("/user/me")
    public Principal me(Principal principal){
        return principal;
    }
}
