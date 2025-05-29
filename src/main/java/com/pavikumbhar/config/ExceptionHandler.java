package com.pavikumbhar.config;

import io.grpc.Status;
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

                return Status.INVALID_ARGUMENT
                        .withDescription(ex.getMessage())
                        .asException();
            }

            log.error("Internal error in gRPC exception: {}", ex.getMessage(), ex);
            return Status.INTERNAL
                    .withCause(ex)
                    .withDescription(ex.getMessage())
                    .asException();
        };
    }
}
