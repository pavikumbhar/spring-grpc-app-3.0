package com.pavikumbhar.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @JsonProperty("book_id")
    private int bookId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private float price;
    @JsonProperty("pages")
    private int pages;
    @JsonProperty("author_id")
    private int authorId;
}
