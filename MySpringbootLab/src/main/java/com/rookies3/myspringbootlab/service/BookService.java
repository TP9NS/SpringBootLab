package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    // 도서 등록
    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("이미 존재하는 ISBN입니다.", HttpStatus.CONFLICT);
        }

        // Book + BookDetail 생성
        BookDetail detail = BookDetail.builder()
                .description(request.getDetailRequest().getDescription())
                .language(request.getDetailRequest().getLanguage())
                .pageCount(request.getDetailRequest().getPageCount())
                .publisher(request.getDetailRequest().getPublisher())
                .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                .edition(request.getDetailRequest().getEdition())
                .build();

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .bookDetail(detail)
                .build();

        detail.setBook(book); // 연관 관계 설정

        Book saved = bookRepository.save(book);
        return BookDTO.Response.fromEntity(saved);
    }

    // 전체 조회
    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    // ID로 조회
    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    // ISBN으로 조회
    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    // 저자명으로 검색
    public List<BookDTO.Response> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    // 제목으로 검색
    public List<BookDTO.Response> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }
}
