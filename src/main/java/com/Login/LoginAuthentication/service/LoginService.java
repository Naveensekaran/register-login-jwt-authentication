package com.Login.LoginAuthentication.service;

import com.Login.LoginAuthentication.dto.LoginDto;
import com.Login.LoginAuthentication.model.UserDetails;
import com.Login.LoginAuthentication.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService{

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails saveUser(UserDetails userDetails) {
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return loginRepo.save(userDetails);
    }

    public UserDetails findUserByUsername(String username) {
        return loginRepo.findByUsername(username);
    }

    @Cacheable(value = "loginDetails")
    public UserDetails findUserByUsernameAndPassword(LoginDto usernameandpassword) {
        String username = usernameandpassword.getUsername();
        System.out.println("Fetching item from database with id: {}");
        String reqpassword = usernameandpassword.getPassword();
        String encodereqpassword = passwordEncoder.encode(reqpassword);
        UserDetails user = null;
        try {
            Thread.sleep(3000);
            user =  loginRepo.findByUsernameAndPassword(username,encodereqpassword);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }

//    @Override
//    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails user = loginRepo.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
//    }
}
