package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.AuthorCopiesDTO;
import com.example.bookshopsystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        List<Author> allAuthors = authorRepository.findAll();

        Random random = new Random();
        int randomIndex = random.nextInt(allAuthors.size());

        return allAuthors.get(randomIndex);
    }

    @Override
    public List<Author> findAllWhoseFirstNameEndsWith(String ending) {
        return this.authorRepository.findDistinctByFirstNameEndingWith(ending);
    }

    @Override
    public List<AuthorCopiesDTO> findAllCopiesForAuthor() {
        return this.authorRepository.findAllCopiesForAuthor();
    }

    @Override
    public int getTotalBooks(String firstName, String lastName) {
        return this.authorRepository.getTotalBooks(firstName, lastName);
    }
}
