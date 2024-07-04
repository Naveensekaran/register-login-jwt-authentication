package com.Login.LoginAuthentication.controller;

import com.Login.LoginAuthentication.dto.LoginDto;
import com.Login.LoginAuthentication.model.UserDetails;
import com.Login.LoginAuthentication.service.LoginService;
import com.Login.LoginAuthentication.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public UserDetails registerUser(@RequestBody UserDetails userDetails) {
        UserDetails newUser = loginService.saveUser(userDetails);
        System.out.println("User Found "+ newUser.getUsername());
        return newUser;
    }

    @GetMapping("/login")
    public UserDetails login(@RequestBody LoginDto usernameandpassword, HttpServletResponse res) {
        System.out.println("Login Controller hitted ");
        long startTime = System.currentTimeMillis();
        UserDetails user = loginService.findUserByUsernameAndPassword(usernameandpassword);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to fetch item: " + (endTime - startTime) + "ms");

       String token = jwtUtils.generateToken(user);
        res.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        System.out.println(token);
        return user;
    }

    @GetMapping("/{username}")
    public UserDetails getUserByUsername(@PathVariable String username) {
        UserDetails user = loginService.findUserByUsername(username);
        return user;
    }

    @PutMapping("/update")
    public UserDetails updateUser(@RequestBody UserDetails userDetails)
    {
        System.out.println("Update User Details hitted ");
        UserDetails updateUser = loginService.updateUser(userDetails);
        return updateUser;
    }

}
