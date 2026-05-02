package com.onlineakimbank.accountservice.exception;

public class NoSuchAccountException extends RuntimeException {
        public NoSuchAccountException() {
            super("[No such account]");
        }
        public NoSuchAccountException(String message) {
            super(message);
        }
}
