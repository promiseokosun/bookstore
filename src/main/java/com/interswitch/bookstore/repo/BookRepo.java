package com.interswitch.bookstore.repo;

import com.interswitch.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book, Long> {

    @Query(value = " select * from tbl_book b where " +
            " ( :title is null or :title = '' or lower(b.title) like lower(concat('%', :title, '%')) ) and " +
            " ( :author is null or :author = '' or lower(b.author) like lower(concat('%', :author, '%')) ) and " +
            " ( :genre is null  or :genre = '' or b.genre=:genre ) and " +
            " ( :yearOfPublication is null or b.year_of_publication=:yearOfPublication ) ", nativeQuery = true)
    Page<Book> searchBooks(String title,
                           String author,
                           String genre,
                           Integer yearOfPublication,
                           Pageable pageable);
}
