package com.example.bookshopsystem;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.Book;
import com.example.bookshopsystem.repositories.AuthorRepository;
import com.example.bookshopsystem.repositories.BookRepository;
import com.example.bookshopsystem.services.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*  seedService.seedAuthors();
        seedService.seedCategories();
        seedService.seedBooks(); */
        // this.booksAfter();

        // this.findAuthorWithBookAfter();
        // this.findAuthorsByNumberOfBooks();
        this.findBooksByAuthor();
    }

    private void booksAfter() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> booksAfter2000 = this.bookRepository.findByReleaseDateAfter(year2000);

        booksAfter2000.forEach(b -> System.out.println(b.getTitle()));
    }

    private void findAuthorWithBookAfter() {
        LocalDate year1990 = LocalDate.of(1990, 12 ,31);

        List<Author> authorsWithBookBefore1990 =
                this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authorsWithBookBefore1990.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void findAuthorsByNumberOfBooks() {
        List<Author> authorsByBooks =
                this.authorRepository.findAllByOrderByBooks();

        authorsByBooks.sort(Comparator.comparing(a -> a.getBooks().size()));

        for (int i = authorsByBooks.size() - 1; i >= 0 ; i--) {
            System.out.printf("%s %s - Count books: %d%n",
                    authorsByBooks.get(i).getFirstName(),
                    authorsByBooks.get(i).getLastName(),
                    authorsByBooks.get(i).getBooks().size());
        }
    }

    private void findBooksByAuthor(){
        Author author = this.authorRepository.findAuthorByFirstNameAndLastName("George", "Powell");
        List<Book> booksByAuthor = this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author);

        booksByAuthor.forEach(b ->
                System.out.printf("Title: %s Release Date: %s Copies: %d%n",
                        b.getTitle(),b.getReleaseDate(), b.getCopies()));
    }
}
