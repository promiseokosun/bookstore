package com.interswitch.bookstore.bootstrap;

import com.interswitch.bookstore.enums.Genre;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.repo.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final BookRepo bookRepo;

    @Override
    public void run(String... args) throws Exception {

        List<Book> books = bookRepo.findAll();
        if(books.isEmpty()) {
            bookRepo.saveAll(
                    Arrays.asList(
                            new Book("The Winners", Genre.FICTION, "9780743273567", "J.R.R. Tolkien", 1940, true),
                            new Book("The Great Gatsby", Genre.FICTION, "9780743273565", "F. Scott Fitzgerald", 1925, true),
                            new Book("To Kill a Mockingbird", Genre.MYSTERY, "9780061120084", "J.D. Salinger", 1951, true),
                            new Book("Pride and Prejudice", Genre.THRILLER, "9781503290563", "Jane Austen", 1998, true),
                            new Book("The Hobbit", Genre.HORROR, "9780743273519", "J.R.R. Tolkien", 1892, true),
                            new Book("The Picture of Dorian Gray", Genre.MYSTERY, "9780743273123", "Oscar Wilde", 1929, true),
                            new Book("The Catcher in the Rye", Genre.POETRY, "9780743273123", "Oscar Wilde", 1929, true),
                            new Book("Pride and Prejudice", Genre.SATIRE, "9780743273123", "Oscar Wilde", 1929, true)
                    )
            );
        }
    }
}
