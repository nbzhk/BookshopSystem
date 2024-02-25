package com.example.bookshopsystem.repositories;

import com.example.bookshopsystem.entities.Author;
import com.example.bookshopsystem.entities.AuthorCopiesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findDistinctByBooksReleaseDateBefore(LocalDate year1990);

    List<Author> findAllByOrderByBooks();

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> findDistinctByFirstNameEndingWith(String ending);

    @Query("SELECT a.firstName AS firstName," +
            " a.lastName AS lastName," +
            " sum(b.copies) AS totalCopies" +
            " FROM authors a" +
            " JOIN books b" +
            " group by a.id" +
            " order by totalCopies DESC")
    List<AuthorCopiesDTO> findAllCopiesForAuthor();
}
