package com.me.zev.lang;

public interface ErrorHandler<E extends ParsingError> {

    ErrorHandler<ParsingError> PRINT_ERROR = error -> System.err.println(error.getFormattedErrorMessage());

    void handle(E error);

}
