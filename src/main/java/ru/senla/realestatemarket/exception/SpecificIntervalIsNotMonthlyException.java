package ru.senla.realestatemarket.exception;

public class SpecificIntervalIsNotMonthlyException extends RuntimeException {

    public SpecificIntervalIsNotMonthlyException(String message) {
        super(message);
    }
}
