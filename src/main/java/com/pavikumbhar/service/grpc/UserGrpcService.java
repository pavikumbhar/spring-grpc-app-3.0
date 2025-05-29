package com.pavikumbhar.service.grpc;

import com.pavikumbhar.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class UserGrpcService  extends ReactorUserServiceGrpc.UserServiceImplBase {

    @Override
    public Mono<CreateUserResponse> createUser(Mono<CreateUserRequest> request) {
        request.map(createUserRequest -> {
            log.info("{}" ,createUserRequest.getEmail());
            return createUserRequest;
        });

        return createUser(request);
    }

    @Override
    public Mono<GetUserResponse> getUser(Mono<GetUserRequest> request) {
        return super.getUser(request);
    }

    @Override
    public Mono<GetUserResponse> getUser(GetUserRequest request) {
        log.info("getUser : {}" ,request.getUserId());
        return getUser(request);
    }


}
