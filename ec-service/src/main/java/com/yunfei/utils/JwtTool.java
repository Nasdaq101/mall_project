package com.yunfei.utils;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.yunfei.common.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTool {
    private final JWTSigner jwtSigner;

    public JwtTool(KeyPair keyPair) {
        this.jwtSigner = JWTSignerUtil.createSigner("rs256", keyPair);
    }

    /**
     * create access-token
     * @return access-token
     */
    public String createToken(Long userId, Duration ttl) {
        // 1.create jws
        return JWT.create()
                .setPayload("user", userId)
                .setExpiresAt(new Date(System.currentTimeMillis() + ttl.toMillis()))
                .setSigner(jwtSigner)
                .sign();
    }

    /**
     * serialize token
     *
     * @param token token
     * @return serialize info within token
     */
    public Long parseToken(String token) {
        // 1.validate if token is null?
        if (token == null) {
            throw new UnauthorizedException("need log in");
        }
        // 2.validate and serialize jwt
        JWT jwt;
        try {
            jwt = JWT.of(token).setSigner(jwtSigner);
        } catch (Exception e) {
            throw new UnauthorizedException("invalid token", e);
        }
        // 2.validate if jwt valid?
        if (!jwt.verify()) {
            throw new UnauthorizedException("invalid token");
        }
        // 3.validate expiration (date)
        try {
            JWTValidator.of(jwt).validateDate();
        } catch (ValidateException e) {
            throw new UnauthorizedException("expired token");
        }
        // 4.validate data format
        Object userPayload = jwt.getPayload("user");
        if (userPayload == null) {
            throw new UnauthorizedException("invalid token");
        }

        // 5.serialize data
        try {
           return Long.valueOf(userPayload.toString());
        } catch (RuntimeException e) {
            throw new UnauthorizedException("invalid token");
        }
    }
}
