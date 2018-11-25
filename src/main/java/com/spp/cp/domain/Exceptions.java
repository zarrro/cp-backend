package com.spp.cp.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Exceptions {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class ArgumentNotSetException extends RuntimeException {
        public ArgumentNotSetException(String message) {
            super(message);
        }
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class IllegalOrderStateException extends RuntimeException {};

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public static class IllegalOrderStateTransitionException extends RuntimeException {};

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

}
