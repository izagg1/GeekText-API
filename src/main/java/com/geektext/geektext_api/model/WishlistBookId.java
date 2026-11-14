
package com.geektext.geektext_api.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WishlistBookId implements Serializable {

    private Long wishlistId;
    private Long bookId;

    public WishlistBookId() {
    }

    public WishlistBookId(Long wishlistId, Long bookId) {
        this.wishlistId = wishlistId;
        this.bookId = bookId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishlistBookId)) return false;
        WishlistBookId that = (WishlistBookId) o;
        return Objects.equals(wishlistId, that.wishlistId)
                && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishlistId, bookId);
    }
}
