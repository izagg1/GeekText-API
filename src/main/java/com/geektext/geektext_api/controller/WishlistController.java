
package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.Book;
import com.geektext.geektext_api.model.CartItem;
import com.geektext.geektext_api.model.CartItemId;
import com.geektext.geektext_api.model.User;
import com.geektext.geektext_api.model.Wishlist;
import com.geektext.geektext_api.model.WishlistBook;

import com.geektext.geektext_api.repository.BookRepository;
import com.geektext.geektext_api.repository.CartItemRepository;
import com.geektext.geektext_api.repository.UserRepository;
import com.geektext.geektext_api.repository.WishlistBookRepository;
import com.geektext.geektext_api.repository.WishlistRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistRepository wishlistRepository;
    private final WishlistBookRepository wishlistBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    public WishlistController(
            WishlistRepository wishlistRepository,
            WishlistBookRepository wishlistBookRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            CartItemRepository cartItemRepository) {

        this.wishlistRepository = wishlistRepository;
        this.wishlistBookRepository = wishlistBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // Create a new wishlist
    @PostMapping
    public ResponseEntity<Void> createWishlist(
            @RequestParam Long userId,
            @RequestParam String name) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (name == null || name.isBlank()
                || name.trim().length() > 100) {
            return ResponseEntity.badRequest().build();
        }

        String wishlistName = name.trim();

        // Maximum of three wishlists per user
        if (wishlistRepository.countByUser_Id(userId) >= 3) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Prevent duplicate wishlist names for the same user
        if (wishlistRepository
                .findByUser_IdAndName(userId, wishlistName)
                .isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user.get());
        wishlist.setName(wishlistName);

        wishlistRepository.save(wishlist);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Add a book to a wishlist
    @PostMapping("/{wishlistId}/books/{bookId}")
    public ResponseEntity<Void> addBookToWishlist(
            @PathVariable Long wishlistId,
            @PathVariable Long bookId) {

        Optional<Wishlist> wishlist =
                wishlistRepository.findById(wishlistId);

        Optional<Book> book =
                bookRepository.findById(bookId);

        if (wishlist.isEmpty() || book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Prevent duplicate books
        if (wishlistBookRepository
                .existsByWishlist_IdAndBook_Id(wishlistId, bookId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        WishlistBook wishlistBook =
                new WishlistBook(wishlist.get(), book.get());

        wishlistBookRepository.save(wishlistBook);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Retrieve all books in a wishlist
    @Transactional(readOnly = true)
    @GetMapping("/{wishlistId}/books")
    public ResponseEntity<List<Book>> getWishlistBooks(
            @PathVariable Long wishlistId) {

        if (!wishlistRepository.existsById(wishlistId)) {
            return ResponseEntity.notFound().build();
        }

        List<Book> books = wishlistBookRepository
                .findByWishlist_Id(wishlistId)
                .stream()
                .map(WishlistBook::getBook)
                .toList();

        return ResponseEntity.ok(books);
    }

    // Remove a book from a wishlist and transfer it to the cart
    @Transactional
    @DeleteMapping("/{wishlistId}/books/{bookId}")
    public ResponseEntity<Void> removeBookFromWishlist(
            @PathVariable Long wishlistId,
            @PathVariable Long bookId) {

        Optional<Wishlist> wishlist =
                wishlistRepository.findById(wishlistId);

        Optional<Book> book =
                bookRepository.findById(bookId);

        if (wishlist.isEmpty() || book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!wishlistBookRepository
                .existsByWishlist_IdAndBook_Id(wishlistId, bookId)) {
            return ResponseEntity.notFound().build();
        }

        User user = wishlist.get().getUser();

        CartItemId cartItemId =
                new CartItemId(user.getId(), bookId);

        // Avoid duplicate entries in the shopping cart
        if (!cartItemRepository.existsById(cartItemId)) {
            CartItem cartItem = new CartItem(user, book.get());
            cartItemRepository.save(cartItem);
        }

        // Remove the book from the wishlist
        wishlistBookRepository
                .deleteByWishlist_IdAndBook_Id(wishlistId, bookId);

        return ResponseEntity.noContent().build();
    }
}
