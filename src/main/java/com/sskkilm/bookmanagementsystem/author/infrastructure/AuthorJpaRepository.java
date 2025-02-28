package com.sskkilm.bookmanagementsystem.author.infrastructure;

import com.sskkilm.bookmanagementsystem.author.application.AuthorRepository;
import com.sskkilm.bookmanagementsystem.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, Long>, AuthorRepository {

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("delete from Author a where a.id = :id")
    void bulkDeleteById(@Param("id") Long id);
}
