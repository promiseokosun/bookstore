package com.interswitch.bookstore.service.impl;

import com.interswitch.bookstore.dto.response.BookResponse;
import com.interswitch.bookstore.exception.CustomException;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.repo.BookRepo;
import com.interswitch.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    @Override
    public Page<BookResponse> searchBooks(String title,
                                          String author,
                                          String genre,
                                          Integer year,
                                          Pageable pageable) {

        Page<Book> bookPage = bookRepo.searchBooks(title, author, genre, year, pageable);

        List<BookResponse> bookResponseList =
                bookPage.getContent().stream().map(this::buildBookResponse).collect(Collectors.toList());

        if (bookPage.getContent().isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), Pageable.unpaged(), bookPage.getTotalElements());
        }
        return new PageImpl<>(bookResponseList, Pageable.unpaged(), bookPage.getTotalElements());
    }

    @Override
    public Book findBook(Long bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new CustomException("Book not found", HttpStatus.NOT_FOUND));
    }


    @Override
    public BookResponse buildBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .yearOfPublication(book.getYearOfPublication())
                .isAvailable(book.isAvailable())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }

}
