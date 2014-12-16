package com.mindstar.library.exception;

public class BaseException extends Exception {

    private String message;

    public BaseException() {
        message = "";
    }

    public BaseException(String s) {
        super(s);
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
