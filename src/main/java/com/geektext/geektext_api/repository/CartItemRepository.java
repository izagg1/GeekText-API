
package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.CartItem;
import com.geektext.geektext_api.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    // Finds all cart entries belonging to a specific user
    List<CartItem> findByIdUserId(Long userId);

    // Adds up the prices of all books in the user's cart
    @Query("""
        SELECT COALESCE(SUM(b.price), 0)
        FROM CartItem c
        JOIN c.book b
        WHERE c.id.userId = :userId
        """)
    BigDecimal calculateSubtotal(@Param("userId") Long userId);

}
