package com.Login.LoginAuthentication.service;

import com.Login.LoginAuthentication.dto.LoginDto;
import com.Login.LoginAuthentication.model.UserDetails;
import com.Login.LoginAuthentication.repository.LoginRepo;
import com.Login.LoginAuthentication.utility.JwtUtils;
import com.Login.LoginAuthentication.utility.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private PasswordUtil passwordUtil;


    public UserDetails saveUser(UserDetails userDetails) {
        String hashedPassword = passwordUtil.hashPassword(userDetails.getPassword());
        userDetails.setPassword(hashedPassword);
        return loginRepo.save(userDetails);
    }

    public UserDetails findUserByUsername(String username) {
        return loginRepo.findByUsername(username);
    }

    @Cacheable(value = "loginDetails")
    public UserDetails findUserByUsernameAndPassword(LoginDto usernameandpassword) {
        System.out.println("service called");
        UserDetails user = loginRepo.findByUsername(usernameandpassword.getUsername());
        if(user == null){
            return null;
        }

        String rawPassword = usernameandpassword.getPassword();
        String storedHashedPassword = user.getPassword();
        System.out.println("Stored hashed password: " + storedHashedPassword);

        Boolean login = passwordUtil.verifyPassword(rawPassword, storedHashedPassword);
        System.out.println("Login Status "+login);

        if (login == false){
            return null;
        }
        return user;
    }

    public UserDetails updateUser(UserDetails userDetails) {
        UserDetails existingUser = loginRepo.findById(userDetails.getId()).orElse(null);
        if (existingUser != null) {
            String hashedPassword = passwordUtil.hashPassword(userDetails.getPassword());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(hashedPassword);
            return loginRepo.save(existingUser);
        }
        return null;
    }


    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = loginRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
