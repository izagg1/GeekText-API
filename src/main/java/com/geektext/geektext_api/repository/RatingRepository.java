package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.bookId = :bookId")
    Double findAverageByBookId(@Param("bookId") Long bookId);
}