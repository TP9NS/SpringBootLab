package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 어플리케이션 시작 시 Book과 BookDetail 샘플 데이터를 자동으로 생성하는 러너 클래스
 */
@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class BookDataInsertRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    /**
     * 어플리케이션 시작 시 실행되는 메서드
     * Book과 BookDetail 샘플 데이터를 생성합니다.
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Starting Book-only data initialization...");

        // 기존 데이터가 있는지 확인 (Publisher 없는 Book이 있는지)
        long existingBookCount = bookRepository.count();
        
        // 이미 많은 데이터가 있다면 skip
        if (existingBookCount >= 15) {
            log.info("Sufficient book data already exists ({} books), skipping Book-only initialization", existingBookCount);
            return;
        }

        // Book과 BookDetail 생성
        createBooksWithDetails();

        log.info("Book-only data initialization completed successfully");
    }

    /**
     * Book과 BookDetail 샘플 데이터를 생성합니다.
     * Publisher 정보는 null로 설정됩니다.
     */
    private void createBooksWithDetails() {
        log.info("Creating books with details (no publisher)...");

        // 1. 프로그래밍 관련 도서들
        Book javaBasics = createBookWithDetail(
                "Java 기초 완전정복", "김자바", "978-8979148123", 25000,
                LocalDate.of(2023, 1, 15),
                "자바 프로그래밍의 기초부터 심화까지 다루는 완벽 가이드북",
                "Korean", 480, "한국출판사", null, "1판"
        );

        Book pythonCookbook = createBookWithDetail(
                "Python Cookbook", "David Beazley", "978-1449340377", 42000,
                LocalDate.of(2022, 5, 20),
                "Python 프로그래밍 실무 레시피와 기법들",
                "English", 706, "O'Reilly Media", "https://example.com/python-cookbook.jpg", "3rd Edition"
        );

        Book webDevelopment = createBookWithDetail(
                "모던 웹 개발", "이웹개발", "978-8960778245", 35000,
                LocalDate.of(2023, 3, 10),
                "HTML5, CSS3, JavaScript를 활용한 최신 웹 개발 기법",
                "Korean", 520, "웹기술출판", "https://example.com/modern-web.jpg", "2판"
        );

        // 2. 데이터베이스 관련 도서들
        Book databaseDesign = createBookWithDetail(
                "Database System Concepts", "Abraham Silberschatz", "978-0073523323", 55000,
                LocalDate.of(2020, 8, 15),
                "데이터베이스 시스템의 개념과 설계에 대한 종합적 안내서",
                "English", 1376, "McGraw-Hill Education", "https://example.com/db-concepts.jpg", "7th Edition"
        );

        Book sqlMastery = createBookWithDetail(
                "SQL 완전정복", "박데이터", "978-8968482977", 28000,
                LocalDate.of(2023, 6, 5),
                "SQL 기초부터 고급 쿼리 작성까지 완벽 마스터",
                "Korean", 650, "데이터베이스출판", null, "개정판"
        );

        // 3. 알고리즘 및 자료구조
        Book algorithms = createBookWithDetail(
                "Introduction to Algorithms", "Thomas H. Cormen", "978-0262033848", 89000,
                LocalDate.of(2021, 12, 1),
                "알고리즘의 설계와 분석에 대한 포괄적 교재",
                "English", 1312, "MIT Press", "https://example.com/algorithms.jpg", "4th Edition"
        );

        Book dataStructures = createBookWithDetail(
                "자료구조와 알고리즘", "정알고리즘", "978-8931436543", 32000,
                LocalDate.of(2022, 11, 20),
                "효율적인 자료구조와 알고리즘 설계 실습서",
                "Korean", 450, "알고리즘출판사", "https://example.com/data-structures.jpg", "3판"
        );

        // 4. 소프트웨어 공학
        Book softwareEngineering = createBookWithDetail(
                "Software Engineering", "Ian Sommerville", "978-0133943030", 72000,
                LocalDate.of(2020, 4, 10),
                "소프트웨어 공학의 이론과 실무를 다루는 종합서",
                "English", 816, "Pearson", "https://example.com/software-eng.jpg", "10th Edition"
        );

        Book cleanArchitecture = createBookWithDetail(
                "클린 아키텍처", "Robert C. Martin", "978-8966262472", 32000,
                LocalDate.of(2019, 8, 25),
                "소프트웨어 구조와 설계의 원칙",
                "Korean", 352, "인사이트", "https://example.com/clean-architecture.jpg", "1판"
        );

        // 5. 인공지능 및 머신러닝
        Book machineLearning = createBookWithDetail(
                "Hands-On Machine Learning", "Aurélien Géron", "978-1492032649", 58000,
                LocalDate.of(2022, 9, 15),
                "실무에 바로 적용할 수 있는 머신러닝 가이드",
                "English", 856, "O'Reilly Media", "https://example.com/ml-hands-on.jpg", "2nd Edition"
        );

        Book deepLearning = createBookWithDetail(
                "딥러닝", "Ian Goodfellow", "978-8968484636", 68000,
                LocalDate.of(2021, 7, 30),
                "딥러닝의 수학적 기초와 실제 구현",
                "Korean", 775, "제이펍", "https://example.com/deep-learning.jpg", "번역판"
        );

        // 상세 정보가 없는 도서들 (BookDetail이 null)
        Book simpleBook1 = Book.builder()
                .title("간단한 프로그래밍 입문서")
                .author("홍길동")
                .isbn("978-8979143210")
                .price(18000)
                .publishDate(LocalDate.of(2023, 2, 28))
                .build();

        Book simpleBook2 = Book.builder()
                .title("Basic Computer Science")
                .author("John Smith")
                .isbn("978-1234567890")
                .price(25000)
                .publishDate(LocalDate.of(2022, 12, 15))
                .build();

        Book simpleBook3 = Book.builder()
                .title("네트워크 보안 기초")
                .author("김보안")
                .isbn("978-8960771234")
                .price(30000)
                .publishDate(LocalDate.of(2023, 4, 20))
                .build();

        // 모든 도서 저장
        List<Book> books = bookRepository.saveAll(
                List.of(javaBasics, pythonCookbook, webDevelopment, databaseDesign, sqlMastery,
                        algorithms, dataStructures, softwareEngineering, cleanArchitecture,
                        machineLearning, deepLearning, simpleBook1, simpleBook2, simpleBook3)
        );

        log.info("Created {} books with/without details", books.size());
    }

    /**
     * Book과 BookDetail을 함께 생성하는 헬퍼 메서드
     * 양방향 연관관계를 올바르게 설정합니다.
     * 
     * @param title 도서 제목
     * @param author 저자
     * @param isbn ISBN
     * @param price 가격
     * @param publishDate 출간일
     * @param description 도서 설명
     * @param language 언어
     * @param pageCount 페이지 수
     * @param publisher 상세 정보의 출판사 (BookDetail의 publisher 필드)
     * @param coverImageUrl 표지 이미지 URL
     * @param edition 판본
     * @return 생성된 Book 엔티티 (BookDetail과 연관관계 설정됨)
     */
    private Book createBookWithDetail(String title, String author, String isbn, Integer price,
                                    LocalDate publishDate, String description, String language,
                                    Integer pageCount, String publisher, String coverImageUrl, String edition) {
        
        // BookDetail 생성
        BookDetail detail = BookDetail.builder()
                .description(description)
                .language(language)
                .pageCount(pageCount)
                .publisher(publisher) // 이것은 BookDetail의 publisher 필드 (String 타입)
                .coverImageUrl(coverImageUrl)
                .edition(edition)
                .build();

        // Book 생성 (Publisher 엔티티는 null)
        Book book = Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .publishDate(publishDate)
                .bookDetail(detail)
                .build();

        // 양방향 연관관계 설정
        detail.setBook(book);

        return book;
    }
}