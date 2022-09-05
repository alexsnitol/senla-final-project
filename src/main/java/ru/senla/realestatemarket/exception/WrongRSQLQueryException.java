package ru.senla.realestatemarket.exception;

public class WrongRSQLQueryException extends RuntimeException {

    public WrongRSQLQueryException(String message) {
        super(message);
    }
}
