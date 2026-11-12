package com.geektext.geektext_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geektext.geektext_api.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookIdOrderByCreatedAtDesc(Long bookId);
}