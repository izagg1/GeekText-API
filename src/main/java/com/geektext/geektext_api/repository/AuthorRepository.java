package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}