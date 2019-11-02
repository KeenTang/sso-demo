package com.kt.sso.client.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-31
 * Time: 23:54
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("TimeInterceptor preHandle");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println("controller类型:" + handlerMethod.getBean().getClass().getName()
                + "方法名:" + handlerMethod.getMethod().getName());
        request.setAttribute("st", System.currentTimeMillis());
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) || StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {
            response.setStatus(401);
            response.getWriter().write("请登录");
        }
        return false;
    }
}
