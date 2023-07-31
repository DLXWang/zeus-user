package com.xinxu.user.filter;

import com.xinxu.user.constant.SignatureHeader;
import com.xinxu.user.model.AccountKeyInfoVO;
import com.xinxu.user.service.IUserManageService;
import com.xinxu.user.util.TokenUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


public class JwtCheckFilter implements HandlerInterceptor {
    private IUserManageService iUserManageService;

    public JwtCheckFilter(IUserManageService iUserManageService) {
        this.iUserManageService = iUserManageService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtCheckIgnore methodAnnotation = handlerMethod.getMethodAnnotation(JwtCheckIgnore.class);
            if (Objects.nonNull(methodAnnotation)) {
                return true;
            }
            String accessToken = request.getHeader(SignatureHeader.ACCESS_TOKEN_HEADER);
            String refreshToken = request.getHeader(SignatureHeader.REFRESH_TOKEN_HEADER);
            // 较大的过期则都过期
            if (Strings.isBlank(accessToken) || Strings.isBlank(refreshToken) || TokenUtils.isTokenExpired(refreshToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("登录过期，重新登录");
                return false;
            }
            if (TokenUtils.isTokenExpired(accessToken)) {
                response.setHeader(SignatureHeader.REFRESH_TOKEN_REQUEST, "true");
            }
            AccountKeyInfoVO accountKeyInfoVO = iUserManageService.authAndAppend(refreshToken);
            request.setAttribute(SignatureHeader.AUTH_USER, accountKeyInfoVO);
        }
        return true;
    }

}

