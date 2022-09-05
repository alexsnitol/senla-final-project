package ru.senla.realestatemarket.exception;

public class PropertySpecificOwnerIsDifferentFromRequestedOwnerException extends RuntimeException {

    public PropertySpecificOwnerIsDifferentFromRequestedOwnerException(String message) {
        super(message);
    }

}
