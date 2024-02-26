package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT b.title AS title, b.editionType AS editionType, " +
            " b.ageRestriction AS ageRestriction, b.price AS price" +
            " FROM books b" +
            " WHERE b.title LIKE :title")
    BookSummaryDTO findSummaryForTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE books b" +
            " SET b.copies = b.copies + :amount" +
            " WHERE b.releaseDate > :date")
    int increaseCopiesForBooksAfter(LocalDate date, int amount);


    @Transactional
    int deleteBookByCopiesLessThan(int amount);

}
