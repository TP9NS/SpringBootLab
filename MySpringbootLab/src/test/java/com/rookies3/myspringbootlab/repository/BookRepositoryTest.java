package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.entity.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Publisher publisher;
    private Book book;
    private BookDetail bookDetail;

    @BeforeEach
    void setUp() {
        // Create publisher
        publisher = Publisher.builder()
                .name("Penguin Random House")
                .establishedDate(LocalDate.of(2013, 7, 1))
                .address("1745 Broadway, New York, NY")
                .build();
        entityManager.persistAndFlush(publisher);

        // Create book
        book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("978-0132350884")
                .price(45000)
                .publishDate(LocalDate.of(2008, 8, 1))
                .publisher(publisher)
                .build();
        entityManager.persistAndFlush(book);

        // Create book detail
        bookDetail = BookDetail.builder()
                .description("A handbook of agile software craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .edition("1st Edition")
                .book(book)
                .build();
        entityManager.persistAndFlush(bookDetail);

        book.setBookDetail(bookDetail);
        entityManager.persistAndFlush(book);
    }

    @Test
    void findByIsbn_ShouldReturnBook() {
        // When
        Optional<Book> found = bookRepository.findByIsbn("978-0132350884");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Clean Code");
        assertThat(found.get().getAuthor()).isEqualTo("Robert C. Martin");
    }

    @Test
    void findByIsbn_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Book> found = bookRepository.findByIsbn("000-0000000000");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void findByIdWithAllDetails_ShouldReturnBookWithAllDetails() {
        // When
        Optional<Book> found = bookRepository.findByIdWithAllDetails(book.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getBookDetail()).isNotNull();
        assertThat(found.get().getPublisher()).isNotNull();
        assertThat(found.get().getPublisher().getName()).isEqualTo("Penguin Random House");
    }

    @Test
    void findByPublisherId_ShouldReturnBooks() {
        // When
        List<Book> found = bookRepository.findByPublisherId(publisher.getId());

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void countByPublisherId_ShouldReturnCorrectCount() {
        // When
        Long count = bookRepository.countByPublisherId(publisher.getId());

        // Then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void existsByIsbn_ShouldReturnTrue() {
        // When
        boolean exists = bookRepository.existsByIsbn("978-0132350884");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByIsbn_ShouldReturnFalse() {
        // When
        boolean exists = bookRepository.existsByIsbn("000-0000000000");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void findByAuthorContainingIgnoreCase_ShouldReturnBooks() {
        // When
        List<Book> found = bookRepository.findByAuthorContainingIgnoreCase("martin");

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getAuthor()).contains("Martin");
    }

    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnBooks() {
        // When
        List<Book> found = bookRepository.findByTitleContainingIgnoreCase("clean");

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).contains("Clean");
    }
}