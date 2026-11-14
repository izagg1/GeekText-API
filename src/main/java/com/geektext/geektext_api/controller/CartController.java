
package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.Book;
import com.geektext.geektext_api.model.CartItem;
import com.geektext.geektext_api.model.CartItemId;
import com.geektext.geektext_api.model.User;
import com.geektext.geektext_api.repository.BookRepository;
import com.geektext.geektext_api.repository.CartItemRepository;
import com.geektext.geektext_api.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public CartController(
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/{userId}/books/{bookId}")
    public ResponseEntity<Void> addBookToCart(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);

        // Make sure both records exist before creating a cart entry
        if (user.isEmpty() || book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CartItemId cartItemId = new CartItemId(userId, bookId);

        // The composite key prevents adding the same book twice
        if (cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.status(409).build();
        }

        CartItem cartItem = new CartItem(user.get(), book.get());

        // Saves a new record in the cart_items table
        cartItemRepository.save(cartItem);

        return ResponseEntity.status(201).build();
    }


    @GetMapping("/{userId}/books")
    public ResponseEntity<List<Book>> getCartBooks(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        // Retrieves cart entries and their associated books
        List<Book> books = cartItemRepository.findByIdUserId(userId)
                .stream()
                .map(CartItem::getBook)
                .toList();

        return ResponseEntity.ok(books);
    }


    @GetMapping("/{userId}/subtotal")
    public ResponseEntity<BigDecimal> getCartSubtotal(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        // Calculates the total price using the books in the user's cart
        BigDecimal subtotal = cartItemRepository.calculateSubtotal(userId);

        return ResponseEntity.ok(subtotal);
    }

    @DeleteMapping("/{userId}/books/{bookId}")
    public ResponseEntity<Void> removeBookFromCart(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        CartItemId cartItemId = new CartItemId(userId, bookId);

        // Check if this book exists in the user's cart
        if (!cartItemRepository.existsById(cartItemId)) {
            return ResponseEntity.notFound().build();
        }

        // Remove the matching entry from the cart_items table
        cartItemRepository.deleteById(cartItemId);

        return ResponseEntity.noContent().build();
    }


}
