package com.hmall.interceptor;

import com.hmall.common.utils.UserContext;
import com.hmall.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTool jwtTool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.get token
        String token = request.getHeader("authorization");
        // 2.validate token
        Long userId = jwtTool.parseToken(token);
        // 3.save
        UserContext.setUser(userId);
        // 4. continue
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // clean
        UserContext.removeUser();
    }
}
