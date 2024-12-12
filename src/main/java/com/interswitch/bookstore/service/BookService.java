package com.interswitch.bookstore.service;

import com.interswitch.bookstore.dto.response.BookResponse;
import com.interswitch.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookResponse> searchBooks(String title,
                                   String author,
                                   String genre,
                                   Integer year,
                                   Pageable pageable);

    Book findBook(Long bookId);

    BookResponse buildBookResponse(Book book);
}
