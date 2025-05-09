package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testCreateBook() {
        // Given
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPublishDate(LocalDate.of(2023, 1, 15));
        book.setPrice(30000);

        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(savedBook.getAuthor()).isEqualTo("홍길동");
    }

    @Test
    public void testFindByIsbn() {
        // Given
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPublishDate(LocalDate.of(2023, 1, 15));
        book.setPrice(30000);

        bookRepository.save(book);

        // When
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");

        // Then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    public void testFindByAuthor() {
        // Given
        Book book1 = new Book();
        book1.setTitle("스프링 부트 입문");
        book1.setAuthor("홍길동");
        book1.setIsbn("9788956746425");
        book1.setPublishDate(LocalDate.of(2023, 1, 15));
        book1.setPrice(30000);

        Book book2 = new Book();
        book2.setTitle("JPA 프로그래밍");
        book2.setAuthor("박둘리");
        book2.setIsbn("9788956746432");
        book2.setPublishDate(LocalDate.of(2024, 3, 24));
        book2.setPrice(35000);

        Book book3 = new Book();
        book3.setTitle("스프링 클라우드");
        book3.setAuthor("홍길동");
        book3.setIsbn("9788956746407");
        book3.setPublishDate(LocalDate.of(2024, 7, 8));
        book3.setPrice(38000);

//        bookRepository.save(book1);
//        bookRepository.save(book2);
//        bookRepository.save(book3);
        bookRepository.saveAll(List.of(book1,book2,book3));

        // When
        List<Book> books = bookRepository.findByAuthor("홍길동");

        // Then
        assertThat(books).hasSize(2);
        assertThat(books).extracting("title").contains("스프링 부트 입문","스프링 클라우드");
    }

    @Test
    @Rollback(value = false)
    public void testUpdateBook() {
        // Given
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        Book savedBook = bookRepository.save(book);

        // When
        savedBook.setPrice(32000);
        //Book updatedBook = bookRepository.save(savedBook);

        // Then
        //assertThat(updatedBook.getPrice()).isEqualTo(32000);
        assertThat(savedBook.getPrice()).isEqualTo(32000);
    }

    @Test
    @Rollback(value = false)
    public void testDeleteBook() {
        // Given
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        Book savedBook = bookRepository.save(book);

        // When
        bookRepository.deleteById(savedBook.getId());

        // Then
        assertThat(bookRepository.findById(savedBook.getId())).isEmpty();
    }
}