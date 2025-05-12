package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 등록
    @PostMapping
    public ResponseEntity<BookDTO.Response> createBook(@Valid @RequestBody BookDTO.Request request) {
        BookDTO.Response response = bookService.createBook(request);
        return ResponseEntity.status(201).body(response);
    }

    // 전체 도서 조회
    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // ID로 도서 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    // 저자명으로 검색
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO.Response>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.searchBooksByAuthor(author));
    }

    // 제목으로 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO.Response>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }
}
