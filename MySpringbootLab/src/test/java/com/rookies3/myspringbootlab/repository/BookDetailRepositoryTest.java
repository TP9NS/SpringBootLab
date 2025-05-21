package com.rookies3.myspringbootlab.repository;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.entity.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
//@Transactional
//@DataJpaTest
public class BookDetailRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDetailRepository bookDetailRepository;

    @Test
    public void createBookWithBookDetail() {
        // Given
        Book book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(45)
                .publishDate(LocalDate.of(2008, 8, 1))
                .build();

        BookDetail bookDetail = BookDetail.builder()
                .description("A handbook of agile software craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .coverImageUrl("https://example.com/cleancode.jpg")
                .edition("1st")
                .book(book)
                .build();

        book.setBookDetail(bookDetail);

        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Clean Code");
        assertThat(savedBook.getIsbn()).isEqualTo("9780132350884");
        assertThat(savedBook.getBookDetail()).isNotNull();
        assertThat(savedBook.getBookDetail().getPublisher()).isEqualTo("Prentice Hall");
        assertThat(savedBook.getBookDetail().getPageCount()).isEqualTo(464);
    }

    @Test
    public void findBookByIsbn() {
        // Given
        Book book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(45)
                .publishDate(LocalDate.of(2008, 8, 1))
                .build();

        BookDetail bookDetail = BookDetail.builder()
                .description("A handbook of agile software craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .coverImageUrl("https://example.com/cleancode.jpg")
                .edition("1st")
                .book(book)
                .build();

        book.setBookDetail(bookDetail);
        bookRepository.save(book);

        // When
        Optional<Book> foundBook = bookRepository.findByIsbn("9780132350884");

        // Then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Clean Code");
    }

    @Test
    public void findByIdWithBookDetail() {
        // Given
        Book book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(45)
                .publishDate(LocalDate.of(2008, 8, 1))
                .build();

        BookDetail bookDetail = BookDetail.builder()
                .description("A handbook of agile software craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .coverImageUrl("https://example.com/cleancode.jpg")
                .edition("1st")
                .book(book)
                .build();

        book.setBookDetail(bookDetail);
        Book savedBook = bookRepository.save(book);

        // When
        Optional<Book> foundBook = bookRepository.findByIdWithBookDetail(savedBook.getId());

        // Then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getBookDetail()).isNotNull();
        assertThat(foundBook.get().getBookDetail().getPublisher()).isEqualTo("Prentice Hall");
    }

    @Test
    public void findBooksByAuthor() {
        // Given
        Book book1 = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .build();

        Book book2 = Book.builder()
                .title("Clean Architecture")
                .author("Robert C. Martin")
                .isbn("9780134494166")
                .build();

        Book book3 = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .build();

        bookRepository.saveAll(List.of(book1, book2, book3));

        // When
        List<Book> martinBooks = bookRepository.findByAuthorContainingIgnoreCase("martin");

        // Then
        assertThat(martinBooks).hasSize(2);
        assertThat(martinBooks).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Clean Code", "Clean Architecture");
    }

    @Test
    public void findBookDetailByBookId() {
        // Given
        Book book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .price(45)
                .publishDate(LocalDate.of(2008, 8, 1))
                .build();

        BookDetail bookDetail = BookDetail.builder()
                .description("A handbook of agile software craftsmanship")
                .language("English")
                .pageCount(464)
                .publisher("Prentice Hall")
                .coverImageUrl("https://example.com/cleancode.jpg")
                .edition("1st")
                .book(book)
                .build();

        book.setBookDetail(bookDetail);
        Book savedBook = bookRepository.save(book);

        // When
        Optional<BookDetail> foundBookDetail = bookDetailRepository.findByBookId(savedBook.getId());

        // Then
        assertThat(foundBookDetail).isPresent();
        assertThat(foundBookDetail.get().getDescription()).contains("agile software craftsmanship");
    }


    //Publisher 추가
    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void createBookWithPublisher() {
        // Given
        Publisher publisher = Publisher.builder()
                .name("한빛미디어")
                .address("서울시 마포구")
                .establishedDate(LocalDate.of(2000, 1, 1))
                .build();

        publisher = publisherRepository.save(publisher);

        Book book = Book.builder()
                .title("스프링 부트 실전 활용")
                .author("홍길동")
                .isbn("9781234567890")
                .price(30000)
                .publishDate(LocalDate.of(2024, 1, 1))
                .publisher(publisher)
                .build();

        bookRepository.save(book);

        // Then
        List<Book> books = bookRepository.findByPublisherId(publisher.getId());
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getPublisher().getName()).isEqualTo("한빛미디어");

        Long count = bookRepository.countByPublisherId(publisher.getId());
        assertThat(count).isEqualTo(1L);
    }

    @Test
    public void findBookWithAllDetails() {
        // Given
        Publisher publisher = Publisher.builder()
                .name("인사이트")
                .address("경기도 성남시")
                .establishedDate(LocalDate.of(2010, 3, 1))
                .build();

        publisherRepository.save(publisher);

        BookDetail detail = BookDetail.builder()
                .description("실전 예제로 배우는 스프링")
                .language("Korean")
                .pageCount(500)
                .publisher("인사이트")
                .coverImageUrl(null)
                .edition("1판")
                .build();

        Book book = Book.builder()
                .title("스프링 마스터")
                .author("박자바")
                .isbn("9780001112223")
                .price(35000)
                .publishDate(LocalDate.of(2023, 9, 1))
                .publisher(publisher)
                .bookDetail(detail)
                .build();

        detail.setBook(book);
        bookRepository.save(book);

        // When
        Optional<Book> result = bookRepository.findByIdWithAllDetails(book.getId());

        // Then
        assertThat(result).isPresent();
        Book found = result.get();
        assertThat(found.getPublisher().getName()).isEqualTo("인사이트");
        assertThat(found.getBookDetail().getPageCount()).isEqualTo(500);
    }

}