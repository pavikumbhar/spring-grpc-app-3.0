package com.pavikumbhar.service.grpc;

import com.pavikumbhar.exception.AppException;
import com.pavikumbhar.grpc.Author;
import com.pavikumbhar.grpc.Book;
import com.pavikumbhar.grpc.BookAuthorServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
//@Service
public class BookAuthorService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {

    @Override
    public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
        log.info("getAuthor() :Fetching books for author with ID: {}", request.getAuthorId());

        try {
            Author author = TempDB.getAuthorsFromTempDb()
                    .stream()
                    .filter(a -> a.getAuthorId() == request.getAuthorId())
                    .findFirst()
                    .orElseThrow(() -> new AppException("Author not found"));

            responseObserver.onNext(author);
            responseObserver.onCompleted();
        } catch (AppException e) {
            log.error("Exception occurred: {}", e.getMessage());
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription("The requested Author ID cannot be found.")
                            .withCause(e).asRuntimeException()
            );
        } catch (Exception exception) {
            log.error("Unexpected exception: {}", exception.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An unexpected error occurred.").withCause(exception)
                    .asRuntimeException()

            );
        }
    }


    @Override
    public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
        log.info("getBooksByAuthor(): Fetching books for author with ID: {}", request.getAuthorId());

        try {
            List<Book> bookList = TempDB.getBooksFromTempDb()
                    .stream()
                    .filter(book -> book.getAuthorId() == request.getAuthorId())
                    .toList();
            if (!bookList.isEmpty()) {
                bookList.forEach(responseObserver::onNext);
            } else {
                responseObserver.onError(Status.NOT_FOUND.withDescription("No books found for the specified author.")
                        .asRuntimeException());
            }
        } catch (Exception e) {
            log.error("Error fetching books: {}", e.getMessage());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred while fetching books.").withCause(e)
                    .asRuntimeException()
            );
        } finally {
            responseObserver.onCompleted();
        }
    }


}
