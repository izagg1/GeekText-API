
package com.geektext.geektext_api.controller;

import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.geektext.geektext_api.model.Book;
import com.geektext.geektext_api.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    @GetMapping("/top-sellers")
    public List<Book> getTopSellers() {
        return bookRepository.findTop10ByOrderByCopiesSoldDesc();
    }

    @GetMapping("/rating/{rating}")
    public List<Book> getBooksByRating(@PathVariable double rating) {
        return bookRepository.findBooksByMinimumRating(rating);
    }


    @PatchMapping("/discount")
    @Transactional
    public ResponseEntity<Void> discountBooksByPublisher(
            @RequestParam String publisher,
            @RequestParam BigDecimal percent) {

        if (percent.compareTo(BigDecimal.ZERO) < 0 ||
                percent.compareTo(new BigDecimal("100")) > 0) {
            return ResponseEntity.badRequest().build();
        }

        List<Book> books = bookRepository.findByPublisherIgnoreCase(publisher);

        BigDecimal multiplier = BigDecimal.ONE.subtract(
                percent.divide(new BigDecimal("100"))
        );

        for (Book book : books) {
            BigDecimal newPrice = book.getPrice()
                    .multiply(multiplier)
                    .setScale(2, RoundingMode.HALF_UP);

            book.setPrice(newPrice);
        }

        bookRepository.saveAll(books);

        return ResponseEntity.noContent().build();
    }


}
