package com.example.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.acl.Owner;


@Table(name = "books")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String id;
    private String bookName;
    private String author;

    @ManyToOne
    @JoinColumn(name = "username")
    private User publisher;

}
