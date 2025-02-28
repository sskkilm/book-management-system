package com.sskkilm.bookmanagementsystem.author.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public static Author create(AuthorCreate command) {
        return Author.builder()
                .name(command.name())
                .email(command.email())
                .build();
    }

    public boolean hasNoChanges(AuthorUpdate command) {
        return Objects.equals(this.name, command.name()) &&
                Objects.equals(this.email, command.email());
    }

    public boolean hasEmailChange(AuthorUpdate command) {
        return !Objects.equals(this.email, command.email());
    }

    public void update(AuthorUpdate command) {
        this.name = command.name();
        this.email = command.email();
    }
}
