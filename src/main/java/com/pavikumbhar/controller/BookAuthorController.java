package com.pavikumbhar.controller;

import com.pavikumbhar.client.BookAuthorServiceClient;
import com.pavikumbhar.model.AuthorDto;
import com.pavikumbhar.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BookAuthorController {

    private final BookAuthorServiceClient bookAuthorServiceClient;

 /*   @GetMapping
    public Mono<AuthorDto> getAuthor(int authorId) {
        return bookAuthorServiceClient.getAuthor(authorId);
    }*/

    @GetMapping
    public AuthorDto getAuthor(int authorId) {
        return bookAuthorServiceClient.getAuthorB(authorId);
    }

    @GetMapping("/author-rc")
    public Mono<AuthorDto> getAuthorRc(int authorId) {
        return bookAuthorServiceClient.getAuthorRc(authorId);
    }

    @GetMapping("/book")
    public List<BookDto> getBooksByAuthor(int authorId) {
        return bookAuthorServiceClient.getBooksByAuthor(authorId);
    }

    @GetMapping("/book-rc")
    public Flux<BookDto> getBooksByAuthorRc(int authorId) {
        return bookAuthorServiceClient.getBooksByAuthorRc(authorId);
    }
}
