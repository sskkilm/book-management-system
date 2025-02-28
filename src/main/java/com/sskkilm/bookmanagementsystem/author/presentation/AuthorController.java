package com.sskkilm.bookmanagementsystem.author.presentation;

import com.sskkilm.bookmanagementsystem.author.application.AuthorService;
import com.sskkilm.bookmanagementsystem.author.presentation.request.AuthorCreateRequest;
import com.sskkilm.bookmanagementsystem.author.presentation.request.AuthorUpdateRequest;
import com.sskkilm.bookmanagementsystem.author.presentation.response.AuthorCreateResponse;
import com.sskkilm.bookmanagementsystem.author.presentation.response.AuthorDetail;
import com.sskkilm.bookmanagementsystem.author.presentation.response.AuthorsResponse;
import com.sskkilm.bookmanagementsystem.common.presentation.ApiErrorResponse;
import com.sskkilm.bookmanagementsystem.common.presentation.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.*;

@Tag(name = "Author", description = "저자 관련 API 명세를 제공합니다.")
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @Operation(
            summary = "저자 생성",
            description = "저자를 생성합니다."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse({ALREADY_EXIST_EMAIL, INVALID_PARAMETER, UNKNOWN})
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<AuthorCreateResponse>> create(@RequestBody @Valid AuthorCreateRequest request) {
        return ApiSuccessResponse.created(
                AuthorCreateResponse.from(
                        authorService.create(request.toCommand())
                )
        );
    }

    @Operation(
            summary = "저자 목록 조회",
            description = "저자 목록을 조회합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse(UNKNOWN)
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<AuthorsResponse>> getList() {
        return ApiSuccessResponse.ok(
                AuthorsResponse.from(authorService.getList())
        );
    }

    @Operation(
            summary = "저자 상세 조회",
            description = "저자의 상세 정보를 조회합니다.",
            parameters = {@Parameter(name = "id", description = "저자 id", required = true)}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiSuccessResponse.class)
            )
    )
    @ApiErrorResponse({AUTHOR_NOT_FOUND, INVALID_PARAMETER, UNKNOWN})
    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<AuthorDetail>> getDetailById(@PathVariable Long id) {
        return ApiSuccessResponse.ok(AuthorDetail.from(authorService.getById(id)));
    }

    @Operation(
            summary = "저자 수정",
            description = "저자 정보를 수정합니다.",
            parameters = {@Parameter(name = "id", description = "저자 id", required = true)}
    )
    @ApiResponse(responseCode = "204")
    @ApiErrorResponse({AUTHOR_NOT_FOUND, ALREADY_EXIST_EMAIL, INVALID_PARAMETER, UNKNOWN})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @RequestBody @Valid AuthorUpdateRequest request,
            @PathVariable Long id
    ) {
        authorService.update(request.toCommand(id));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "저자 삭제",
            description = "저자 정보를 삭제합니다.",
            parameters = {@Parameter(name = "id", description = "저자 id", required = true)}
    )
    @ApiResponse(responseCode = "204")
    @ApiErrorResponse({AUTHOR_NOT_FOUND, INVALID_PARAMETER, UNKNOWN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
