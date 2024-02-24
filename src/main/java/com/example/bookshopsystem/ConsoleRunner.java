package com.example.bookshopsystem;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.entities.EditionType;
import com.example.bookshopsystem.repositories.AuthorRepository;
import com.example.bookshopsystem.repositories.BookRepository;
import com.example.bookshopsystem.services.AuthorService;
import com.example.bookshopsystem.services.BookService;
import com.example.bookshopsystem.services.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final AuthorService authorService;


    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookService = bookService;

        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedService.seedAuthors();
       /* seedService.seedCategories();
        seedService.seedBooks(); */
        // this.booksAfter();

        // this.findAuthorWithBookAfter();
        // this.findAuthorsByNumberOfBooks();
        // this.findBooksByAuthor();

        // Exercises: Spring Data Advanced Querying:
        // P01BooksTitlesByAgeRestriction();
        // P02GoldenBooks();
        // P03BooksByPrice();
        // P04NotReleasedBooks();
        // P05BooksReleasedBeforeDate();
        // P06AuthorsSearch();
        // P07BooksSearch();
        P08BookTitlesSearch();
    }

    private void P08BookTitlesSearch() {
        Scanner scanner = new Scanner(System.in);
        String startsWith = scanner.nextLine();

        this.bookService.findByAuthorLastNameStartingWith(startsWith)
                .forEach(b -> System.out.printf("%s (%s %s)%n",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    private void P07BooksSearch() {
        Scanner scanner = new Scanner(System.in);
        String part = scanner.nextLine();

        this.bookService.findAllTitlesContaining(part)
                .forEach(System.out::println);
    }

    private void P06AuthorsSearch() {
        Scanner scanner = new Scanner(System.in);
        String endsWith = scanner.nextLine();

        this.authorService.findAllWhoseFirstNameEndsWith(endsWith)
                .forEach(a -> System.out.printf("%s %s%n", a.getFirstName(), a.getLastName()));
    }

    private void P05BooksReleasedBeforeDate() {
        Scanner scanner = new Scanner(System.in);
        String[] dateData = scanner.nextLine().split("-");
        int day = Integer.parseInt(dateData[0]);
        int month = Integer.parseInt(dateData[1]);
        int year = Integer.parseInt(dateData[2]);

        LocalDate releaseDate = LocalDate.of(year, month, day);

        this.bookService.findByReleaseDateBefore(releaseDate)
                .forEach(b -> System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType(), b.getPrice()));
    }

    private void P04NotReleasedBooks() {
        Scanner scanner = new Scanner(System.in);
        int year = Integer.parseInt(scanner.nextLine());

        this.bookService.findAllTitlesNotReleaseInYear(year)
                .forEach(System.out::println);
    }

    private void P03BooksByPrice() {
        this.bookService.findAllByPriceRange(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(b -> System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));
    }

    private void P02GoldenBooks() {
        this.bookService.findAllTitlesByEditionTypeAndCopiesLessThen(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void P01BooksTitlesByAgeRestriction() {
        Scanner scanner = new Scanner(System.in);
        String ageRestriction = scanner.nextLine().toUpperCase();

        this.bookService.findAllTitlesByAgeRestriction(ageRestriction)
                .forEach(System.out::println);
    }

    private void booksAfter() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> booksAfter2000 = this.bookRepository.findByReleaseDateAfter(year2000);

        booksAfter2000.forEach(b -> System.out.println(b.getTitle()));
    }

    private void findAuthorWithBookAfter() {
        LocalDate year1990 = LocalDate.of(1990, 12, 31);

        List<Author> authorsWithBookBefore1990 =
                this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authorsWithBookBefore1990.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void findAuthorsByNumberOfBooks() {
        List<Author> authorsByBooks =
                this.authorRepository.findAllByOrderByBooks();

        authorsByBooks.sort(Comparator.comparing(a -> a.getBooks().size()));

        for (int i = authorsByBooks.size() - 1; i >= 0; i--) {
            System.out.printf("%s %s - Count books: %d%n",
                    authorsByBooks.get(i).getFirstName(),
                    authorsByBooks.get(i).getLastName(),
                    authorsByBooks.get(i).getBooks().size());
        }
    }

    private void findBooksByAuthor() {
        Author author = this.authorRepository.findAuthorByFirstNameAndLastName("George", "Powell");
        List<Book> booksByAuthor = this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author);

        booksByAuthor.forEach(b ->
                System.out.printf("Title: %s Release Date: %s Copies: %d%n",
                        b.getTitle(), b.getReleaseDate(), b.getCopies()));
    }
}
