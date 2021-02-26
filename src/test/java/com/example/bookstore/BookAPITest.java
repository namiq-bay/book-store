package com.example.bookstore;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.dto.BookDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class BookAPITest {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpEntity request;
    private String url;

    @BeforeEach
    public void setUp() throws JSONException {
        String authStr = "publisher:qfrxxxadygrlwtfz";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        restTemplate = new RestTemplate();
        url = "http://localhost:8081/books";
        headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    //  This method returns the book matching the provided book ID.
    @Test
    public void testGetBookById() {
        request = new HttpEntity(headers);

        ResponseEntity<BookDTO> response = restTemplate.exchange(
                url.concat("/bc633c81-4f03-4dae-9c33-3935a1226f52"),
                HttpMethod.GET, request, BookDTO.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getId(), Matchers.equalTo("bc633c81-4f03-4dae-9c33-3935a1226f52"));
    }

    // This method returns all books
    @Test
    public void testGetAllBooks() {
        request = new HttpEntity(headers);

        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                url.concat("/all"), HttpMethod.GET, request,
                new ParameterizedTypeReference<List<BookDTO>>() {
                });

        List<String> bookNameList = response.getBody().stream().map(e -> e.getBookName()).collect(Collectors.toList());
        MatcherAssert.assertThat(bookNameList, Matchers.containsInAnyOrder(
                "Spring Boot in Action",
                "Head First Java",
                "Spring Microservices in Action",
                "Algorithms",
                "Clean Code"));
    }

    // This method returns all books published by specific publisher.
    @Test
    public void testGetAllBooksByPublisher() {
        request = new HttpEntity(headers);

        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                url.concat("/publisher?publisherName=publisher"),
                HttpMethod.GET, request, new ParameterizedTypeReference<List<BookDTO>>() {
                });

        List<String> publishers = response.getBody()
                .stream().map(e -> e.getPublisher())
                .collect(Collectors.toList());

        MatcherAssert.assertThat(publishers, Matchers.containsInRelativeOrder("publisher"));
    }

    // This method returns all books matching the provided book name (Pageable)
    @Test
    public void testSearchBooksByName() {
        request = new HttpEntity(headers);

        ResponseEntity<CustomPageImpl<BookDTO>> response = restTemplate.exchange(
                url.concat("?bookName=Java&pageNo=0&pageSize=10"),
                HttpMethod.GET, request,
                new ParameterizedTypeReference<CustomPageImpl<BookDTO>>() {
                });

        List<String> books = response.getBody()
                .stream().map(b -> b.getBookName())
                .collect(Collectors.toList());

        MatcherAssert.assertThat(books, Matchers.contains("Head First Java"));
    }


    // This method adds new book to the book list.
    @Test
    public void testAddBook() throws JSONException {

        JSONObject bookJSON = new JSONObject();
        bookJSON.put("bookName", "Clean Code");
        bookJSON.put("author", "Robert C. Martin");

        request = new HttpEntity(bookJSON.toString(), headers);

        ResponseEntity<URI> response = restTemplate.exchange(url.concat("/create"),
                HttpMethod.POST, request, URI.class);

        ResponseEntity<BookDTO> bookResponse = restTemplate.exchange(
                response.getHeaders().getLocation(),
                HttpMethod.GET, request, BookDTO.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(201));
        MatcherAssert.assertThat(bookResponse.getBody().getBookName(), Matchers.equalTo("Clean Code"));
    }

    // This method updates the book details matching the provided book ID.
    @Test
    public void testUpdateBook() throws JSONException {

        JSONObject bookJSON = new JSONObject();
        bookJSON.put("bookName", "Introduction to Algorithms");
        bookJSON.put("author", "Thomas H. Cormen");
        request = new HttpEntity(bookJSON.toString(), headers);

        ResponseEntity<BookDTO> response = restTemplate.exchange(url.concat("/update/65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea"),
                HttpMethod.PUT, request, BookDTO.class);

        MatcherAssert.assertThat(response.getBody().getBookName(), Matchers.equalTo("Introduction to Algorithms"));
    }

    // This method deletes the book matching the provided book ID.

    @Test
    public void deleteBook() {
        String id = "65a8f5ec-a2f3-40f4-88af-f0ecc1c937ea";

        request = new HttpEntity(headers);
        restTemplate.exchange(url.concat("/delete/" + id),
                HttpMethod.DELETE, request, Object.class);

        try {
            restTemplate.exchange(url.concat("/" + id), HttpMethod.GET, request, BookDTO.class);
            Assertions.fail();
        } catch (Exception e) {
        }
    }
}
