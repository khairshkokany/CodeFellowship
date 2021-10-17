package com.example.CodeFellowship.repositery;


import com.example.CodeFellowship.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositeryData extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByUsername(String username);
    ApplicationUser findApplicationUserById(Long id);
}
