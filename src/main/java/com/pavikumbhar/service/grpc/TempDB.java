package com.pavikumbhar.service.grpc;

import com.pavikumbhar.grpc.Author;
import com.pavikumbhar.grpc.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TempDB {

	public static List<Author> getAuthorsFromTempDb() {
		return List.of(Author.newBuilder()
                .setAuthorId(1)
                .setBookId(1)
                .setFirstName("Charles")
                .setLastName("Dickens")
                .setGender("male")
                .build(), Author.newBuilder()
                .setAuthorId(2)
                .setFirstName("William")
                .setLastName("Shakespeare")
                .setGender("male")
                .build(), Author.newBuilder()
                .setAuthorId(3)
                .setFirstName("JK")
                .setLastName("Rowling")
                .setGender("female")
                .build(), Author.newBuilder()
                .setAuthorId(4)
                .setFirstName("Virginia")
                .setLastName("Woolf")
                .setGender("female")
                .build());
	}

	public static List<Book> getBooksFromTempDb() {
		return List.of(Book.newBuilder()
                .setBookId(1)
                .setAuthorId(1)
                .setTitle("Oliver Twist")
                .setPrice(123.3f)
                .setPages(100)
                .build(), Book.newBuilder()
                .setBookId(2)
                .setAuthorId(1)
                .setTitle("A Christmas Carol")
                .setPrice(223.3f)
                .setPages(150)
                .build(), Book.newBuilder()
                .setBookId(3)
                .setAuthorId(2)
                .setTitle("Hamlet")
                .setPrice(723.3f)
                .setPages(250)
                .build(), Book.newBuilder()
                .setBookId(4)
                .setAuthorId(3)
                .setTitle("Harry Potter")
                .setPrice(423.3f)
                .setPages(350)
                .build(), Book.newBuilder()
                .setBookId(5)
                .setAuthorId(3)
                .setTitle("The Casual Vacancy")
                .setPrice(523.3f)
                .setPages(450)
                .build(), Book.newBuilder()
                .setBookId(6)
                .setAuthorId(4)
                .setTitle("Mrs. Dalloway")
                .setPrice(623.3f)
                .setPages(550)
                .build());
	}

}