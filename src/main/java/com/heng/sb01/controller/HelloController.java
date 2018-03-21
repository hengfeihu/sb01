package com.heng.sb01.controller;

import com.heng.sb01.config.shiro.JWTToken;
import com.heng.sb01.entity.Result;
import com.heng.sb01.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping(value = "login", method = RequestMethod.GET)
    Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String jwtToken = JwtUtil.sign(username, password);
        SecurityUtils.getSubject().login(new JWTToken(jwtToken));
        return new Result().success("success", jwtToken);
    }
}
