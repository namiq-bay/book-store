package com.example.bookstore.repo;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAll();
    Book getById(String id);
    Page<Book> findByBookNameContains(String bookName, Pageable pageable);
    List<Book> getAllByPublisher(User publisher);
}

