package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.AuthorCopiesDTO;

import java.util.List;

public interface AuthorService {

    Author getRandomAuthor();

    List<Author> findAllWhoseFirstNameEndsWith(String ending);

    List<AuthorCopiesDTO> findAllCopiesForAuthor();

}
