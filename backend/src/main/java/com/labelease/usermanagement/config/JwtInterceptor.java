package com.labelease.usermanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labelease.usermanagement.common.JwtUtil;
import com.labelease.usermanagement.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 鉴权拦截器 - 非公开接口需携带有效 Token
 * 验证通过后，将 currentUsername 和 currentRole 注入 request attribute，
 * 供下游 Controller 读取以进行角色/本人范围判断。
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 放行 OPTIONS 请求（CORS 预检）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtUtil.validateToken(token)) {
                // 将用户身份信息注入 request，供 Controller 读取
                request.setAttribute("currentUsername", jwtUtil.getUsernameFromToken(token));
                request.setAttribute("currentRole", jwtUtil.getRoleFromToken(token));
                return true;
            }
        }

        // 未通过鉴权
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                Result.unauthorized("未登录或令牌已过期，请重新登录")));
        return false;
    }
}
