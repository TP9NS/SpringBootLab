package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book("스프링 부트 입문", "홍길동", "9788956746425", 30000, LocalDate.of(2025, 5, 7));
        Book saved = bookRepository.save(book);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testFindByIsbn() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746432", 35000, LocalDate.of(2025, 4, 30));
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("9788956746432");
        assertThat(found).isPresent();
        assertThat(found.get().getAuthor()).isEqualTo("박둘리");
    }

    @Test
    void testFindByAuthor() {
        Book book = new Book("JPA 프로그래밍", "박둘리", "9788956746432", 35000, LocalDate.of(2025, 4, 30));
        bookRepository.save(book);

        List<Book> books = bookRepository.findByAuthor("박둘리");
        assertThat(books).hasSize(1);
    }

    @Test
    void testUpdateBook() {
        Book book = bookRepository.save(new Book("책", "저자", "1234", 10000, LocalDate.now()));
        book.setTitle("수정된 책");
        Book updated = bookRepository.save(book);
        assertThat(updated.getTitle()).isEqualTo("수정된 책");
    }

    @Test
    void testDeleteBook() {
        Book book = bookRepository.save(new Book("삭제할 책", "저자", "5678", 15000, LocalDate.now()));
        bookRepository.delete(book);
        Optional<Book> found = bookRepository.findById(book.getId());
        assertThat(found).isEmpty();
    }
}