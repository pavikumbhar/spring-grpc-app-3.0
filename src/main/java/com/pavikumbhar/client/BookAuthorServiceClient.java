package com.pavikumbhar.client;

import com.pavikumbhar.exception.AppException;
import com.pavikumbhar.grpc.Author;
import com.pavikumbhar.grpc.Book;
import com.pavikumbhar.grpc.BookAuthorServiceGrpc;
import com.pavikumbhar.grpc.ReactorBookAuthorServiceGrpc;
import com.pavikumbhar.model.AuthorDto;
import com.pavikumbhar.model.BookDto;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * @author pavikumbhar
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BookAuthorServiceClient {

    private final ReactorBookAuthorServiceGrpc.ReactorBookAuthorServiceStub reactorBookAuthorServiceStub;
    private final BookAuthorServiceGrpc.BookAuthorServiceBlockingStub authorServiceBlockingStub;

    /** Retrieves an Author reactively using gRPC **/
    public Mono<AuthorDto> getAuthor(int authorId) {
        return reactorBookAuthorServiceStub.getAuthor(Author.newBuilder().setAuthorId(authorId).build())
                .map(this::convertToAuthorDto)
                .doOnError(e -> log.error("Error fetching author {}: {}", authorId, e.getMessage(), e))
                .onErrorResume(e -> Mono.error(new AppException("Failed to fetch author", e)));
    }

    /** Retrieves an Author reactively with improved exception handling **/
    public Mono<AuthorDto> getAuthorRc(int authorId) {
        var authorRequest = Author.newBuilder().setAuthorId(authorId).build();
        log.info("getAuthorRc author ID: {}", authorId);
        return reactorBookAuthorServiceStub.getAuthor(authorRequest)
                .map(this::convertToAuthorDto)
                .doOnError(e -> log.error("Error fetching author {}: {}", authorId, e.getMessage(), e))
                .onErrorResume(e -> Mono.error(new AppException("Failed to fetch author", e)));
    }

    /** Retrieves an Author synchronously with gRPC blocking stub **/
    public AuthorDto getAuthorB(int authorId) {
        log.info("Fetching author ID: {}", authorId);
        try {
            return convertToAuthorDto(authorServiceBlockingStub.getAuthor(
                    Author.newBuilder().setAuthorId(authorId).build()));
        } catch (StatusRuntimeException ex) {
            log.error("gRPC error fetching author {}: {}", authorId, ex.getStatus().getDescription(), ex);
            throw new AppException("Failed to fetch author due to gRPC error", ex);
        } catch (Exception e) {
            log.error("Unexpected error fetching author {}: {}", authorId, e.getMessage(), e);
            throw new AppException("Failed to fetch author due to an unexpected error", e);
        }
    }

    /** Converts gRPC Author object to DTO **/
    private AuthorDto convertToAuthorDto(Author author) {
        return AuthorDto.builder()
                .authorId(author.getAuthorId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .gender(author.getGender())
                .bookId(author.getBookId())
                .build();
    }

    /** Retrieves Books synchronously by Author **/
    public List<BookDto> getBooksByAuthor(int authorId) {
        try {
            Iterator<Book> books = authorServiceBlockingStub.getBooksByAuthor(
                    Author.newBuilder().setAuthorId(authorId).build());
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(books, Spliterator.ORDERED), false)
                    .map(this::convertToBookDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching books by author {}: {}", authorId, e.getMessage(), e);
            throw new AppException("Failed to fetch books by author", e);
        }
    }

    /** Retrieves Books reactively using gRPC **/
    public Flux<BookDto> getBooksByAuthorRc(int authorId) {
        log.info("Fetching books for author ID: {}", authorId);
        return reactorBookAuthorServiceStub.getBooksByAuthor(Author.newBuilder().setAuthorId(authorId).build())
                .map(this::convertToBookDto)
                .onErrorResume(e -> {
                    log.error("Error fetching books by author {}: {}", authorId, e.getMessage(), e);
                    return Mono.error(new AppException("Failed to fetch books by author", e));
                });
    }

    /** Converts gRPC Book object to DTO **/
    private BookDto convertToBookDto(Book book) {
        return BookDto.builder()
                .bookId(book.getBookId())
                .authorId(book.getAuthorId())
                .title(book.getTitle())
                .price(book.getPrice())
                .pages(book.getPages())
                .build();
    }
}
