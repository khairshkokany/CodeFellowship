package com.example.CodeFellowship.repositery;

import com.example.CodeFellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositeryPost extends JpaRepository <Post , Long> {
    Post findPostById(Long post_id);
}
