package com.njglyy.corporate_group_backend.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.Token;
import com.njglyy.corporate_group_backend.mapper.TokenMapper;
import com.njglyy.corporate_group_backend.utils.HttpContextUtil;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenString = request.getHeader("token");
        String username = request.getHeader("username");
        LocalDateTime expirationTime = LocalDateTime.now();
        Token token = tokenMapper.expirationCheck(tokenString,username);
        if(token!=null){
            if (token.getExpiration_time().isBefore(expirationTime)){
                setReturn(response,401,"用户未登录，请先登录");
                return false;
            }
            else{
                tokenMapper.expirationExtend(expirationTime.plusMinutes(60), tokenString);
                return true;
            }

        }
        else{
            setReturn(response,401,"用户未登录，请先登录");
            return false;
        }
    }

    private static void setReturn(HttpServletResponse response, Integer code, String msg) throws IOException, IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result(code,msg,"");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        httpResponse.getWriter().print(json);
    }


}
