package com.sskkilm.bookmanagementsystem.common.config;

import com.sskkilm.bookmanagementsystem.common.error.ErrorCode;
import com.sskkilm.bookmanagementsystem.common.error.ErrorResponse;
import com.sskkilm.bookmanagementsystem.common.presentation.ApiErrorResponse;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.Builder;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer customizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorResponse apiErrorResponse = handlerMethod
                    .getMethodAnnotation(ApiErrorResponse.class);

            if (apiErrorResponse != null) {
                generateApiResponse(operation, apiErrorResponse.value());
            }

            return operation;
        };
    }

    private void generateApiResponse(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> map = Arrays.stream(errorCodes)
                .map(
                        errorCode -> ExampleHolder
                                .builder()
                                .holder(getSwaggerExample(errorCode))
                                .name(errorCode.name())
                                .code(errorCode.getStatus().value())
                                .build()
                )
                .collect(Collectors.groupingBy(ExampleHolder::code));

        for (Integer code : map.keySet()) {
            List<ExampleHolder> exampleHolders = map.get(code);

            MediaType mediaType = new MediaType();
            exampleHolders.forEach(
                    exampleHolder -> mediaType.addExamples(
                            exampleHolder.name(),
                            exampleHolder.holder()
                    )
            );
            Content content = new Content();
            content.addMediaType("application/json", mediaType);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.content(content);
            apiResponse.setDescription(HttpStatus.valueOf(code).name());

            responses.addApiResponse(String.valueOf(code), apiResponse);
        }
    }

    private Example getSwaggerExample(ErrorCode errorCode) {
        ErrorResponse<String> response = new ErrorResponse<>(
                errorCode.name(), errorCode.getMessage(), "실제 발생한 에러의 세부 정보가 여기에 포함됩니다."
        );
        Example example = new Example();
        example.setValue(response);

        return example;
    }

    @Builder
    private record ExampleHolder(String name, Example holder, int code) {
    }
}
