package com.kt.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-18
 * Time: 22:51
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SsoClient2Application {
    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Application.class, args);
    }
}
