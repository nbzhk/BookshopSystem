package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.*;
import com.example.bookshopsystem.repositories.AuthorRepository;
import com.example.bookshopsystem.repositories.BookRepository;
import com.example.bookshopsystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private static final String RESOURCE_PATH = "src\\main\\resources\\files";
    private static final String AUTHORS_FILE_PATH = RESOURCE_PATH + "\\authors.txt";
    private static final String BOOKS_FILE_PATH = RESOURCE_PATH + "\\books.txt";
    private static final String CATEGORIES_FILE_PATH = RESOURCE_PATH + "\\categories.txt";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, CategoryRepository categoryRepository, AuthorService authorService, CategoryService categoryService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        for (String currentAuthor : Files.readAllLines(Path.of(AUTHORS_FILE_PATH))) {
            String firstName = currentAuthor.split("\\s+")[0];
            String lastName = currentAuthor.split("\\s+")[1];

            Author author = new Author(firstName, lastName);
            authorRepository.save(author);
        }


    }

    @Override
    public void seedCategories() throws IOException {
        for (String c : Files.readAllLines(Path.of(CATEGORIES_FILE_PATH))) {
            if (!c.isBlank()) {
                Category category = new Category(c);
                categoryRepository.save(category);
            }
        }

    }

    @Override
    public void seedBooks() throws IOException {
        for (String bookData : Files.readAllLines(Path.of(BOOKS_FILE_PATH))) {
            String[] split = bookData.split("\\s+");

            int editionTypeIndex = Integer.parseInt(split[0]);
            EditionType editionType = EditionType.values()[editionTypeIndex];

            LocalDate publishDate = LocalDate.parse(split[1],
                    DateTimeFormatter.ofPattern("d/M/yyyy"));

            int copies = Integer.parseInt(split[2]);
            BigDecimal price = new BigDecimal(split[3]);

            int ageRestrictionIndex = Integer.parseInt(split[4]);
            AgeRestriction ageRestriction = AgeRestriction.values()[ageRestrictionIndex];

            String title = Arrays.stream(split)
                    .skip(5)
                    .collect(Collectors.joining(" "));

            Author author = authorService.getRandomAuthor();
            Set<Category> categories = categoryService.getRandomCategories();

            Book book = new Book(title, editionType, price, publishDate, ageRestriction, author, categories, copies);

            bookRepository.save(book);

        }


    }
}
