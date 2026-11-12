package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.Comment;
import com.geektext.geektext_api.model.Rating;
import com.geektext.geektext_api.repository.BookRepository;
import com.geektext.geektext_api.repository.CommentRepository;
import com.geektext.geektext_api.repository.RatingRepository;
import com.geektext.geektext_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books/{bookId}")
public class RatingCommentController {

    private final RatingRepository ratingRepository;
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public RatingCommentController(RatingRepository ratingRepository,
                                   CommentRepository commentRepository,
                                   BookRepository bookRepository,
                                   UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/ratings")
    public ResponseEntity<Void> createRating(@PathVariable Long bookId,
                                             @RequestBody Rating rating) {
        if (rating.getUserId() == null || rating.getRating() == null
                || rating.getRating() < 1 || rating.getRating() > 5) {
            return ResponseEntity.badRequest().build();
        }
        if (!bookRepository.existsById(bookId) || !userRepository.existsById(rating.getUserId())) {
            return ResponseEntity.notFound().build();
        }
        rating.setId(null);
        rating.setBookId(bookId);
        rating.setCreatedAt(LocalDateTime.now());
        ratingRepository.save(rating);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long bookId,
                                              @RequestBody Comment comment) {
        if (comment.getUserId() == null || comment.getComment() == null
                || comment.getComment().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (!bookRepository.existsById(bookId) || !userRepository.existsById(comment.getUserId())) {
            return ResponseEntity.notFound().build();
        }
        comment.setId(null);
        comment.setBookId(bookId);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentRepository.findByBookIdOrderByCreatedAtDesc(bookId));
    }

    @GetMapping("/ratings/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            return ResponseEntity.notFound().build();
        }
        Double avg = ratingRepository.findAverageByBookId(bookId);
        double result = (avg == null) ? 0.0 : Math.round(avg * 100.0) / 100.0;
        return ResponseEntity.ok(result);
    }
}