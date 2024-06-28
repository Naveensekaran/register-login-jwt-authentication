package com.Login.LoginAuthentication.repository;

import com.Login.LoginAuthentication.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<UserDetails, Long> {
    UserDetails findByUsername(String username);

    UserDetails findByUsernameAndPassword(String username, String password);
}
