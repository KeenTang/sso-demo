package com.kt.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-17
 * Time: 22:37
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private static final String PUBLIC_KEY = "PUBLIC_KEY";//"PUBLIC_KEY!@#$%^&*()";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";//"PRIVATE_KEY)(*&^%$#@!";
    private static KeyFactory keyFactory;
    private static Map<String, Key> keyPairMap = new HashMap<>(2);

    static {
       /* try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }*/
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        keyPairMap.put(PUBLIC_KEY, keyPair.getPublic());
        keyPairMap.put(PRIVATE_KEY, keyPair.getPrivate());
    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client1")
                .secret(passwordEncoder.encode("client1"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:8091/client1/login","http://127.0.0.1:8091/client1/login")
                .scopes("all")
                .autoApprove(true)
                .and()
                .withClient("client2")
                .secret(passwordEncoder.encode("client2"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:8092/client2/login","http://127.0.0.1:8092/client2/login")
                .scopes("all")
                .autoApprove(true)
                .and()
                .withClient("vue").secret(passwordEncoder.encode("vue"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:9999/login","http://127.0.0.1:9999/login")
                .scopes("all")
                .autoApprove(true);
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
       // jwtAccessTokenConverter.setKeyPair(new KeyPair(getPublicKey(), getPrivateKey()));
        jwtAccessTokenConverter.setSigningKey("kt1991");
        return jwtAccessTokenConverter;
    }

    private PrivateKey getPrivateKey() {
       /* try {
            return keyFactory.generatePrivate(getKeySpec(PRIVATE_KEY));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }*/
        return (PrivateKey) keyPairMap.get(PRIVATE_KEY);
    }

    private PublicKey getPublicKey() {
       /* try {
            return keyFactory.generatePublic(getKeySpec(PUBLIC_KEY));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }*/
        return (PublicKey) keyPairMap.get(PUBLIC_KEY);
    }

    private KeySpec getKeySpec(String key) {
        byte[] encode = Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8));
        return new X509EncodedKeySpec(encode);
    }



}
