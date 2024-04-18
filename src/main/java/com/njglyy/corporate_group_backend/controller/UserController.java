package com.njglyy.corporate_group_backend.controller;

import com.njglyy.corporate_group_backend.entity.Result;
import com.njglyy.corporate_group_backend.entity.Token;
import com.njglyy.corporate_group_backend.entity.User;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.TokenMapper;
import com.njglyy.corporate_group_backend.mapper.corporateGroup.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.njglyy.corporate_group_backend.utils.Encrypt;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin//解决跨域问题
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenMapper tokenMapper;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result queryUsers(@RequestBody User user) {
        System.out.println(user);
        System.out.println("user");
        Encrypt encrypt = new Encrypt();
        String encryptedPassword = encrypt.VerifyPassword(user.getUsername(), user.getPassword());
        List<User> userList = userMapper.queryUser(user.getUsername(), encryptedPassword);
        if (userList.size() == 0) {
            return new Result(400, "账号密码错误！", null);

        }

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(60);
        String tokenString = UUID.randomUUID().toString();
        Token token =new Token(user.getUsername(), tokenString, expirationTime);

        tokenMapper.createToken(user.getUsername(), tokenString, expirationTime);
        if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=.,])(?=\\S+$).{8,}$")) {


            return new Result(300, "密码较弱，请及时修改！", token);
        }

        return new Result(200, "欢迎登录!", token);


    }

    @RequestMapping(value = "/autoLogin", method = RequestMethod.GET)
    public Result autoLogin(@RequestHeader("token") String tokenString, HttpServletRequest request) {
        String username = request.getHeader("username");
        Token token = tokenMapper.expirationCheck(tokenString,username);
        LocalDateTime expirationTime = LocalDateTime.now();
        if (token != null && token.getExpiration_time().isAfter(expirationTime)) {
            return new Result(200, "欢迎登录!", token.getUsername());
        }
        return new Result(400, "已过期", null);


    }
}
