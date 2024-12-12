package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.dto.response.apiresponse.ApiResponse;
import com.interswitch.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam(required = false) String title,
                                         @RequestParam(required = false) String author,
                                         @RequestParam(required = false) Integer year,
                                         @RequestParam(required = false) String genre,
                                         Pageable pageable) {

        return new ResponseEntity<>(
                ApiResponse.successResponse(bookService.searchBooks(title, author, genre, year, pageable)), HttpStatus.OK);
    }
}
