package com.pavikumbhar.config;


import com.pavikumbhar.grpc.BookAuthorServiceGrpc;
import com.pavikumbhar.grpc.ReactorBookAuthorServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcConfiguration {
   /*
    private ManagedChannel getManagedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();
    }

    @Bean
    public ReactorBookAuthorServiceGrpc.ReactorBookAuthorServiceStub reactorBookAuthorServiceStub() {
        return ReactorBookAuthorServiceGrpc.newReactorStub(getManagedChannel());
    }
    */
    @Bean
    public ReactorBookAuthorServiceGrpc.ReactorBookAuthorServiceStub reactorBookAuthorServiceStub(GrpcChannelFactory channels) {
        return ReactorBookAuthorServiceGrpc.newReactorStub(channels.createChannel("0.0.0.0:9090"));
    }

    @Bean
    public BookAuthorServiceGrpc.BookAuthorServiceBlockingStub authorServiceBlockingStub1(GrpcChannelFactory channels) {
        return BookAuthorServiceGrpc.newBlockingStub(channels.createChannel("0.0.0.0:9090"));
    }

    @Bean
    public BookAuthorServiceGrpc.BookAuthorServiceBlockingStub authorServiceBlockingStub(GrpcChannelFactory channels) {
        return BookAuthorServiceGrpc.newBlockingStub(channels.createChannel("book-author"));
    }
}
