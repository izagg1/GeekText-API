
package com.geektext.geektext_api.repository;

import com.geektext.geektext_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenreIgnoreCase(String genre);


    List<Book> findTop10ByOrderByCopiesSoldDesc();


    List<Book> findByPublisherIgnoreCase(String publisher);

    @Query("""
    SELECT b FROM Book b
    WHERE b.id IN (
        SELECT r.bookId
        FROM Rating r
        GROUP BY r.bookId
        HAVING AVG(r.rating) >= :minimumRating
    )
    """)
    List<Book> findBooksByMinimumRating(
            @Param("minimumRating") double minimumRating);



}
