package com.sskkilm.bookmanagementsystem.book.infrastructure;

import com.sskkilm.bookmanagementsystem.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookJpaRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    @Query("select b from Book b join fetch b.author where b.id = :id")
    Optional<Book> findByIdWithAuthor(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("delete from Book b where b.author.id = :authorId")
    void deleteAllBooksByAuthorId(@Param("authorId") Long authorId);
}