package com.sskkilm.bookmanagementsystem.book.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.sskkilm.bookmanagementsystem.book.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Book> findAllByConditionWithPaging(LocalDate publicationDate, Pageable pageable) {
        List<Book> books = jpaQueryFactory
                .selectFrom(book)
                .where(publicationDateEq(publicationDate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(book.count())
                .from(book)
                .where(publicationDateEq(publicationDate))
                .fetchOne();

        return new PageImpl<>(books, pageable, count);
    }

    private BooleanExpression publicationDateEq(LocalDate publicationDate) {
        return publicationDate != null ? book.publicationDate.eq(publicationDate) : null;
    }
}
