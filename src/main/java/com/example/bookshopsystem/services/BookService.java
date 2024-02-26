package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.BookSummaryDTO;
import com.example.bookshopsystem.entities.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> findAllTitlesByAgeRestriction(String restriction);

    List<String> findAllTitlesByEditionTypeAndCopiesLessThen(EditionType editionType, int copies);

    List<Book> findAllByPriceRange(BigDecimal startPrice, BigDecimal endPrice);

    List<String> findAllTitlesNotReleaseInYear(int year);

    List<Book> findByReleaseDateBefore(LocalDate releaseDate);

    List<String> findAllTitlesContaining(String part);

    List<Book> findByAuthorLastNameStartingWith(String startsWith);

    int countBooksWithTitleGreaterThan(int number);

    BookSummaryDTO getInformationForTitle(String title);

    int increaseCopiesForBookAfter(String date, int amount);

    int removeBooksWithCopiesLowerThan(int lowerThan);
}
