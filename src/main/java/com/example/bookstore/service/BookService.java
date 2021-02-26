package com.example.bookstore.service;


import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.model.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<BookDTO> findSpecificBooks(String bookName, Pageable pageable);

    List<BookDTO> findAllBooks();

    List<BookDTO> getByPublisher(String publisher);

    BookDTO getBook(String id);

    Book addBook(Book book);

    void deleteBook(String id);

    BookDTO updateBook(String id, Book book);
}
