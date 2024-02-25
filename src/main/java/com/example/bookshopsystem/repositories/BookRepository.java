package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.AgeRestriction;
import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByReleaseDateAfter(LocalDate releaseDate);
    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lessThan, BigDecimal greaterThan);

    List<Book> findByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findByReleaseDateBefore(LocalDate releaseDate);

    List<Book> findByTitleContaining(String part);

    List<Book> findByAuthorLastNameStartingWith(String startsWith);

    @Query("SELECT COUNT(b.title) FROM books b WHERE length(b.title) > :length")
    int countBookByTitleLongerThan(int length);
}
