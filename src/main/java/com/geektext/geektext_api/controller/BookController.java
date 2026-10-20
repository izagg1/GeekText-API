package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.Book;
import com.geektext.geektext_api.repository.BookRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        return repo.findByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody Book book) {
        if (repo.findByIsbn(book.getIsbn()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        book.setId(null);
        repo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}