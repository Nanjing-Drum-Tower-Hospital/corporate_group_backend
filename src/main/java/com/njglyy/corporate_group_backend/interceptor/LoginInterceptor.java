package com.njglyy.corporate_group_backend.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.njglyy.corporate_group_backend.entity.Token;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.TokenMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenString = request.getHeader("token");
        String username = request.getHeader("username");
        Token token = tokenMapper.expirationCheck(tokenString,username);
        if(token!=null){
            return true;
        }
        else{
            return false;
        }
    }
}
