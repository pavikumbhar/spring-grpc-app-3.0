package com.pavikumbhar.config;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;

@Slf4j
@Configuration
public class ExceptionHandler {

    @Bean
    public GrpcExceptionHandler grpcExceptionHandler() {
        return ex -> {
            if (ex instanceof IllegalArgumentException) {
                log.error("Invalid argument error in gRPC exception: {}", ex.getMessage(), ex);
                Metadata metadata = new Metadata();
                metadata.put(Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER), "INVALID_ARGUMENT");
                return Status.INVALID_ARGUMENT.withDescription(ex.getMessage())
                        .asException(metadata);
            }

            log.error("Internal error in gRPC exception: {}", ex.getMessage(), ex);
            return Status.INTERNAL
                    .withCause(ex)
                    .withDescription(ex.getMessage())
                    .asException();
        };
    }
}
