package com.example.bookstore.service.impl;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.model.dto.BookDTO;
import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final UserService userService;

    @Override
    public Page<BookDTO> findSpecificBooks(String bookName, Pageable pageable) {
        return repository
                .findByBookNameContains(bookName, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public List<BookDTO> findAllBooks() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBook(String id) {
        return mapToDTO(findBook(id));
    }

    @Override
    public List<BookDTO> getByPublisher(String publisherName) {
        User publisher = userService.findByUsername(publisherName);

        return repository.getAllByPublisher(publisher)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Book addBook(Book book) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User user = userService.findByUsername(authentication.getName());

        return repository.save(
                Book.builder().
                        id(UUID.randomUUID().toString())
                        .bookName(book.getBookName())
                        .author(book.getAuthor())
                        .publisher(user)
                        .build()
        );
    }

    @Override
    public void deleteBook(String id) {
        repository.delete(findBook(id));
    }

    @Override
    public BookDTO updateBook(String id, Book book) {
        Book tmp = findBook(id);
        tmp.setBookName(book.getBookName());
        tmp.setAuthor(book.getAuthor());

        return mapToDTO(repository.save(tmp));
    }

    BookDTO mapToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .publisher(book.getPublisher().getUsername())
                .build();
    }

    Book findBook(String id) {
        Book book = repository.getById(id);
        if (book == null)
            throw new BookNotFoundException(id);

        return book;
    }
}
