package com.xinxu.user.util;

import com.xinxu.user.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    // 生成 JWT Token
    public String generateToken(String subject, boolean isRefreshToken) {
        long expirationTime = isRefreshToken ? jwtConfig.getRefresh_token_expiration_time() : jwtConfig.getAccess_token_expiration_time();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getPrivate_key())
                .compact();
    }

    // 验证和解析 JWT Token
    public Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtConfig.getPrivate_key())
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            // Token 解析失败或已过期
            return null;
        }
    }

    // 验证 Token 是否过期（ 无用，过期parseToken 返回就为null ）
    public boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);
        return claims != null && claims.getExpiration().before(new Date());
    }

    // 续长令牌过期时间（刷新 Token）
    public String refreshToken(String refreshToken, boolean isRefreshToken) {
        Claims claims = parseToken(refreshToken);
        if (claims != null) {
            if (claims.getExpiration().before(new Date())) {
                return null;
            }
            String username = claims.getSubject();
            if (Strings.isBlank(username)) {
                return null;
            }
            // 重新生成一个 Token
            return generateToken(username, isRefreshToken);
        }
        return null;
    }


    // 从token中获取登录用户名
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = parseToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
}
