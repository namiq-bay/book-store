package com.example.bookstore.api;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.dto.BookDTO;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookAPI {

    private final BookService service;

    @GetMapping
    public ResponseEntity<Page<BookDTO>> searchBooks(
            @RequestParam String bookName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<BookDTO> books = service.findSpecificBooks(bookName, pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String id) {
        return ResponseEntity.ok(service.getBook(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(service.findAllBooks());
    }

    @GetMapping("/publisher")
    public ResponseEntity<List<BookDTO>> getByPublisher(@RequestParam String publisherName) {
        return ResponseEntity.ok(service.getByPublisher(publisherName));
    }

    @PostMapping("/create")
    public ResponseEntity<URI> addBook(@RequestBody Book book) {
        String id = service.addBook(book).getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/books/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable String id) {
        service.deleteBook(id);
        return ResponseEntity.accepted().build();
    }

}
