package com.sskkilm.bookmanagementsystem.book.presentation;

import com.sskkilm.bookmanagementsystem.book.application.BookService;
import com.sskkilm.bookmanagementsystem.book.presentation.request.BookCreateRequest;
import com.sskkilm.bookmanagementsystem.book.presentation.request.BookUpdateRequest;
import com.sskkilm.bookmanagementsystem.book.presentation.response.BookCreateResponse;
import com.sskkilm.bookmanagementsystem.book.presentation.response.BookDetail;
import com.sskkilm.bookmanagementsystem.book.presentation.response.BookDto;
import com.sskkilm.bookmanagementsystem.book.presentation.response.PageResult;
import com.sskkilm.bookmanagementsystem.common.presentation.ApiErrorResponse;
import com.sskkilm.bookmanagementsystem.common.presentation.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.*;

@Tag(name = "Book", description = "도서 관련 API 명세를 제공합니다.")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @Operation(
            summary = "도서 생성",
            description = "도서를 생성합니다."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse({ALREADY_EXIST_ISBN, AUTHOR_NOT_FOUND, INVALID_PARAMETER, UNKNOWN})
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<BookCreateResponse>> create(@RequestBody @Valid BookCreateRequest request) {
        return ApiSuccessResponse.created(
                BookCreateResponse.from(
                        bookService.create(request.toCommand())
                )
        );
    }

    @Operation(
            summary = "도서 목록 조회",
            description = "도서 목록을 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse({INVALID_PARAMETER, UNKNOWN})
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResult<BookDto>>> getListByConditionWithPaging(
            @RequestParam(name = "publication_date", required = false) LocalDate publicationDate,
            @Min(value = 1, message = "페이지 번호는 1보다 작을 수 없습니다.")
            @RequestParam(required = false, defaultValue = "1") int page,
            @Min(value = 1, message = "페이지 크기는 1보다 작을 수 없습니다.")
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.ok(
                PageResult.from(
                        bookService.getListByConditionWithPaging(
                                publicationDate, PageRequest.of(page - 1, size)
                        )
                )
        );
    }

    @Operation(
            summary = "도서 상세 조회",
            description = "도서의 상세 정보를 조회합니다.",
            parameters = {@Parameter(name = "id", description = "도서 id", required = true)}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse({BOOK_NOT_FOUND, INVALID_PARAMETER, UNKNOWN})
    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<BookDetail>> getDetailById(@PathVariable Long id) {
        return ApiSuccessResponse.ok(
                BookDetail.from(bookService.getByIdWithAuthor(id))
        );
    }

    @Operation(
            summary = "도서 수정",
            description = "도서 정보를 수정합니다.",
            parameters = {@Parameter(name = "id", description = "도서 id", required = true)}
    )
    @ApiResponse(responseCode = "204")
    @ApiErrorResponse({BOOK_NOT_FOUND, ALREADY_EXIST_ISBN, INVALID_PARAMETER, UNKNOWN})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @RequestBody @Valid BookUpdateRequest request,
            @PathVariable Long id
    ) {
        bookService.update(request.toCommand(id));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "도서 삭제",
            description = "도서 정보를 삭제합니다.",
            parameters = {@Parameter(name = "id", description = "도서 id", required = true)}
    )
    @ApiResponse(responseCode = "204")
    @ApiErrorResponse({BOOK_NOT_FOUND, INVALID_PARAMETER, UNKNOWN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
