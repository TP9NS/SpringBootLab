package com.rookies3.myspringbootlab.controller.dto;

import com.rookies3.myspringbootlab.entity.Publisher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Publisher name is required")
        @Size(max = 100, message = "Publisher name cannot exceed 100 characters")
        private String name;

        @PastOrPresent(message = "Established date cannot be in the future")
        private LocalDate establishedDate;

        @Size(max = 200, message = "Address cannot exceed 200 characters")
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private Long bookCount;
        private List<BookDTO.SimpleResponse> books;

        public static Response fromEntity(Publisher publisher) {
            return Response.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount((long) publisher.getBooks().size())
                    .books(publisher.getBooks().stream()
                            .map(BookDTO.SimpleResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private Long bookCount;

        public static SimpleResponse fromEntity(Publisher publisher) {
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .build();
        }

        public static SimpleResponse fromEntityWithCount(Publisher publisher, Long bookCount) {
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount(bookCount)
                    .build();
        }
    }
}