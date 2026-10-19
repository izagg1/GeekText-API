
package com.geektext.geektext_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_id")
    private Long bookId;

    public CartItemId() {
    }

    public CartItemId(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    // Compare both IDs to identify the same cart entry
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemId)) return false;

        CartItemId that = (CartItemId) o;

        return Objects.equals(userId, that.userId)
                && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
