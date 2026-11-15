
package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.WishlistBook;
import com.geektext.geektext_api.model.WishlistBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistBookRepository
        extends JpaRepository<WishlistBook, WishlistBookId> {

    // Finds all books belonging to a specific wishlist
    List<WishlistBook> findByWishlist_Id(Long wishlistId);

    // Checks whether a book already exists in a wishlist
    boolean existsByWishlist_IdAndBook_Id(Long wishlistId, Long bookId);

    // Removes a book from a specific wishlist
    void deleteByWishlist_IdAndBook_Id(Long wishlistId, Long bookId);

}
