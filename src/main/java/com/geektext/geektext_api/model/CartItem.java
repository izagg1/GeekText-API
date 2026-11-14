
package com.geektext.geektext_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items") // Maps this class to the cart_items table
public class CartItem {

    // Uses userId and bookId together as the primary key
    @EmbeddedId
    private CartItemId id;

    // Links each cart entry to a user in the database
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // Links each cart entry to a book in the database
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public CartItem() {
    }

    public CartItem(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new CartItemId(user.getId(), book.getId());
    }

    public CartItemId getId() {
        return id;
    }

    public void setId(CartItemId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
