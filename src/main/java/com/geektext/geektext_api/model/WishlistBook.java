
package com.geektext.geektext_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist_books")
public class WishlistBook {

    @EmbeddedId
    private WishlistBookId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("wishlistId")
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public WishlistBook() {
    }

    public WishlistBook(Wishlist wishlist, Book book) {
    this.wishlist = wishlist;
    this.book = book;
}

    public WishlistBookId getId() {
        return id;
    }

    public void setId(WishlistBookId id) {
        this.id = id;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
