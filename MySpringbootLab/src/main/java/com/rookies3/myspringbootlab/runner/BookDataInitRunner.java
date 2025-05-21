package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.entity.Publisher;
import com.rookies3.myspringbootlab.repository.BookRepository;
import com.rookies3.myspringbootlab.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(1) // After DataInitRunner
@RequiredArgsConstructor
@Slf4j
public class BookDataInitRunner implements CommandLineRunner {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Starting book data initialization...");

        // Check if data already exists
        if (publisherRepository.count() > 0) {
            log.info("Publisher data already exists, skipping book initialization");
            return;
        }

        // Create publishers
        List<Publisher> publishers = createPublishers();
        
        // Create books
        createBooks(publishers);

        log.info("Book data initialization completed successfully");
    }

    private List<Publisher> createPublishers() {
        log.info("Creating publishers...");

        Publisher penguinRandomHouse = Publisher.builder()
                .name("Penguin Random House")
                .establishedDate(LocalDate.of(2013, 7, 1))
                .address("1745 Broadway, New York, NY 10019")
                .build();

        Publisher oreilly = Publisher.builder()
                .name("O'Reilly Media")
                .establishedDate(LocalDate.of(1978, 1, 1))
                .address("1005 Gravenstein Highway North, Sebastopol, CA 95472")
                .build();

        Publisher prenticehall = Publisher.builder()
                .name("Prentice Hall")
                .establishedDate(LocalDate.of(1913, 1, 1))
                .address("Old Tappan, NJ")
                .build();

        Publisher manning = Publisher.builder()
                .name("Manning Publications")
                .establishedDate(LocalDate.of(1993, 1, 1))
                .address("20 Baldwin Road, PO Box 761, Shelter Island, NY 11964")
                .build();

        List<Publisher> publishers = publisherRepository.saveAll(
                List.of(penguinRandomHouse, oreilly, prenticehall, manning)
        );

        log.info("Created {} publishers", publishers.size());
        return publishers;
    }

    private void createBooks(List<Publisher> publishers) {
        log.info("Creating books...");

        Publisher penguinRandomHouse = publishers.get(0);
        Publisher oreilly = publishers.get(1);
        Publisher prenticehall = publishers.get(2);
        Publisher manning = publishers.get(3);

        // O'Reilly books
        Book effectiveJava = createBookWithDetail(
                "Effective Java", "Joshua Bloch", "978-0134685991", 38000,
                LocalDate.of(2018, 1, 6), oreilly,
                "A comprehensive guide to the Java programming language and its core libraries",
                "English", 416, "Addison-Wesley Professional", "https://example.com/effective-java.jpg", "3rd Edition"
        );

        Book javaCompletereference = createBookWithDetail(
                "Java: The Complete Reference", "Herbert Schildt", "978-1260440232", 45000,
                LocalDate.of(2017, 5, 26), oreilly,
                "The definitive Java programming guide",
                "English", 1368, "McGraw-Hill Education", "https://example.com/java-complete.jpg", "11th Edition"
        );

        // Prentice Hall books
        Book cleanCode = createBookWithDetail(
                "Clean Code", "Robert C. Martin", "978-0132350884", 42000,
                LocalDate.of(2008, 8, 1), prenticehall,
                "A handbook of agile software craftsmanship",
                "English", 464, "Prentice Hall", "https://example.com/clean-code.jpg", "1st Edition"
        );

        Book designPatterns = createBookWithDetail(
                "Design Patterns", "Gang of Four", "978-0201633612", 55000,
                LocalDate.of(1994, 10, 31), prenticehall,
                "Elements of reusable object-oriented software",
                "English", 395, "Addison-Wesley Professional", "https://example.com/design-patterns.jpg", "1st Edition"
        );

        // Manning books
        Book springInAction = createBookWithDetail(
                "Spring in Action", "Craig Walls", "978-1617294945", 48000,
                LocalDate.of(2018, 10, 20), manning,
                "A practical guide to Spring Framework",
                "English", 520, "Manning Publications", "https://example.com/spring-in-action.jpg", "5th Edition"
        );

        Book microservicesPatterns = createBookWithDetail(
                "Microservices Patterns", "Chris Richardson", "978-1617294549", 52000,
                LocalDate.of(2018, 11, 19), manning,
                "Building applications with microservices",
                "English", 520, "Manning Publications", "https://example.com/microservices-patterns.jpg", "1st Edition"
        );

        // Penguin Random House books
        Book atomicHabits = createBookWithDetail(
                "Atomic Habits", "James Clear", "978-0735211292", 16500,
                LocalDate.of(2018, 10, 16), penguinRandomHouse,
                "An easy and proven way to build good habits and break bad ones",
                "English", 320, "Avery", "https://example.com/atomic-habits.jpg", "1st Edition"
        );

        // Book without detail
        Book bookWithoutDetail = Book.builder()
                .title("The Pragmatic Programmer")
                .author("Andrew Hunt")
                .isbn("978-0135957059")
                .price(34000)
                .publishDate(LocalDate.of(2019, 9, 13))
                .publisher(penguinRandomHouse)
                .build();

        List<Book> books = bookRepository.saveAll(
                List.of(effectiveJava, javaCompletereference, cleanCode, designPatterns,
                        springInAction, microservicesPatterns, atomicHabits, bookWithoutDetail)
        );

        log.info("Created {} books", books.size());
    }

    private Book createBookWithDetail(String title, String author, String isbn, Integer price,
                                     LocalDate publishDate, Publisher publisher,
                                     String description, String language, Integer pageCount,
                                     String detailPublisher, String coverImageUrl, String edition) {
        BookDetail detail = BookDetail.builder()
                .description(description)
                .language(language)
                .pageCount(pageCount)
                .publisher(detailPublisher)
                .coverImageUrl(coverImageUrl)
                .edition(edition)
                .build();

        Book book = Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .publishDate(publishDate)
                .publisher(publisher)
                .bookDetail(detail)
                .build();

        detail.setBook(book);
        return book;
    }
}