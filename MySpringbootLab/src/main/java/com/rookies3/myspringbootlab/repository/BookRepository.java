package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookDetail LEFT JOIN FETCH b.publisher WHERE b.id = :id")
    Optional<Book> findByIdWithAllDetails(@Param("id") Long id);

    List<Book> findByPublisherId(Long publisherId);

    Long countByPublisherId(@Param("publisherId") Long publisherId);

    boolean existsByIsbn(String isbn);
}