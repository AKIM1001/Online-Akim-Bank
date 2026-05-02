package com.onlineakimbank.userservice.exception;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException() {
        super("[No such user]");
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}

