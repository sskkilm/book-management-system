package com.sskkilm.bookmanagementsystem.book.domain;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false, unique = true)
    private String isbn;

    private LocalDate publicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public static Book create(BookCreate command, Author author) {
        return Book.builder()
                .title(command.title())
                .description(command.description())
                .isbn(command.isbn())
                .publicationDate(command.publicationDate())
                .author(author)
                .build();
    }

    public boolean hasNoChanges(BookUpdate command) {
        return Objects.equals(this.title, command.title()) &&
                Objects.equals(this.description, command.description()) &&
                Objects.equals(this.isbn, command.isbn()) &&
                Objects.equals(this.publicationDate, command.publicationDate());
    }

    public boolean hasIsbnChange(BookUpdate command) {
        return !Objects.equals(this.isbn, command.isbn());
    }

    public void update(BookUpdate command) {
        this.title = command.title();
        this.description = command.description();
        this.isbn = command.isbn();
        this.publicationDate = command.publicationDate();
    }
}
