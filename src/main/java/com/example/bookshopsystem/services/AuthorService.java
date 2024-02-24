package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Author;

import java.util.List;

public interface AuthorService {

    Author getRandomAuthor();

    List<Author> findAllWhoseFirstNameEndsWith(String ending);

}
