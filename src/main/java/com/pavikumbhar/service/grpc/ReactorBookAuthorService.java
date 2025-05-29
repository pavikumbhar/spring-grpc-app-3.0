package com.pavikumbhar.service.grpc;

import com.pavikumbhar.grpc.Author;
import com.pavikumbhar.grpc.Book;
import com.pavikumbhar.grpc.ReactorBookAuthorServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@GrpcService()
public class ReactorBookAuthorService extends ReactorBookAuthorServiceGrpc.BookAuthorServiceImplBase {

    @Override
    public Mono<Author> getAuthor(Mono<Author> request) {
        log.info("ReactorBookAuthorService :: getAuthor() ");
      return request
                .map(Author::getAuthorId)
                .map(authorId -> TempDB.getAuthorsFromTempDb()
                        .stream()
                        .filter(author -> author.getAuthorId() == authorId)
                        .findFirst()
         //NOSONAR  .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND.withDescription("authorId " + authorId + " not found in DB"))));
                       .orElseThrow(() -> new IllegalArgumentException("authorId " + authorId +  " not found in DB")));

        /** return request
                .flatMap(author -> Flux.fromIterable(TempDB.getAuthorsFromTempDb())
                        .filter(tempAuthor -> tempAuthor.getAuthorId() == author.getAuthorId())
                        .next() // Get the first matching result as a Mono
                        .switchIfEmpty(Mono.error(new StatusRuntimeException(
                                Status.NOT_FOUND.withDescription("authorId " + author.getAuthorId() + " not found in DB"))))); */
    }


    @Override
    public Mono<Author> getAuthor(Author request) {
        log.info(" getAuthor() authorId :{}",request.getAuthorId());
        return Mono.just(request)
                .map(Author::getAuthorId)
                .map(authorId -> TempDB.getAuthorsFromTempDb()
                        .stream()
                        .filter(author -> author.getAuthorId() == authorId)
                        .findFirst()
                        //NOSONAR  .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND.withDescription("authorId " + authorId + " not found in DB"))));
                        .orElseThrow(() -> new IllegalArgumentException("authorId " + authorId +  " not found in DB")));
    }

    @Override
    public Flux<Book> getBooksByAuthor(Mono<Author> request) {
        log.info(" getBooksByAuthor() ");
        return request
                .map(Author::getAuthorId)
                .map(authorId -> TempDB.getBooksFromTempDb()
                        .stream()
                        .filter(author -> author.getAuthorId() == authorId)
                        .toList())
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<Book> getBooksByAuthor(Author request) {
        log.info("ReactorBookAuthorService :: getBooksByAuthor() authorId : {}", request.getAuthorId());
        return Flux.fromIterable(TempDB.getBooksFromTempDb  ())
                .filter(book -> book.getAuthorId() == request.getAuthorId());
    }

}
