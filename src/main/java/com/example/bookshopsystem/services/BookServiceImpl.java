package com.example.bookshopsystem.services;

import com.example.bookshopsystem.entities.AgeRestriction;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.BookSummaryDTO;
import com.example.bookshopsystem.entities.EditionType;
import com.example.bookshopsystem.repositories.BookRepository;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAllTitlesByAgeRestriction(String restriction) {
        return this.bookRepository.findByAgeRestriction(AgeRestriction.valueOf(restriction));
    }

    @Override
    public List<String> findAllTitlesByEditionTypeAndCopiesLessThen(EditionType editionType, int copies) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByPriceRange(BigDecimal lessThan, BigDecimal greaterThen) {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lessThan, greaterThen);
    }

    @Override
    public List<String> findAllTitlesNotReleaseInYear(int releaseYear ) {
        LocalDate before = LocalDate.of(releaseYear, 1, 1);
        LocalDate after = LocalDate.of(releaseYear, 12, 31);
        return this.bookRepository.findByReleaseDateBeforeOrReleaseDateAfter(before, after)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<Book> findByReleaseDateBefore(LocalDate releaseDate) {
        return this.bookRepository.findByReleaseDateBefore(releaseDate);
    }

    @Override
    public List<String> findAllTitlesContaining(String part) {
        return this.bookRepository.findByTitleContaining(part)
                .stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<Book> findByAuthorLastNameStartingWith(String startsWith) {
        return this.bookRepository.findByAuthorLastNameStartingWith(startsWith);
    }

    @Override
    public int countBooksWithTitleGreaterThan(int number) {
        return this.bookRepository.countBookByTitleLongerThan(number);
    }

    @Override
    public BookSummaryDTO getInformationForTitle(String title) {
        return this.bookRepository.findSummaryForTitle(title);
    }

    @Override
    public int increaseCopiesForBookAfter(String date, int amount) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd MMM yyyy")
                .toFormatter(Locale.ENGLISH);
        LocalDate after = LocalDate.parse(date, formatter);
        return this.bookRepository.increaseCopiesForBooksAfter(after, amount);
    }

    @Override
    public int removeBooksWithCopiesLowerThan(int lowerThan) {
        return this.bookRepository.deleteBookByCopiesLessThan(lowerThan);
    }
}
