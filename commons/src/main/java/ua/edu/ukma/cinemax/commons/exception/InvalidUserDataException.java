package ua.edu.ukma.cinemax.commons.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
