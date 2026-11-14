
package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    // Finds all wishlists belonging to a specific user
    List<Wishlist> findByUser_Id(Long userId);

    // Finds a specific wishlist by its name and owner
    Optional<Wishlist> findByUser_IdAndName(Long userId, String name);

    // Counts how many wishlists a user currently has
    long countByUser_Id(Long userId);

}
