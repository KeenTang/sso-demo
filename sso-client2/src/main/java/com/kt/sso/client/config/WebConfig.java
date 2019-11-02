package com.kt.sso.client.config;

import com.kt.sso.client.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 22:36
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

  /*  @Bean
    public FilterRegistrationBean<TimeFilter> timeFilterRegistrationBean() {
        FilterRegistrationBean<TimeFilter> timeFilterRegistrationBean = new FilterRegistrationBean<>();
        timeFilterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        timeFilterRegistrationBean.setFilter(new TimeFilter());
        return timeFilterRegistrationBean;
    }

   */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
    }
}
