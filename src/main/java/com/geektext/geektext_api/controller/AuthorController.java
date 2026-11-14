package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.Author;
import com.geektext.geektext_api.model.Book;
import com.geektext.geektext_api.repository.AuthorRepository;
import com.geektext.geektext_api.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public AuthorController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
        author.setId(null);
        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookRepository.findByAuthorId(authorId));
    }
    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long authorId) {
        return authorRepository.findById(authorId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}