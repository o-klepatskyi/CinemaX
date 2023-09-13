package ua.edu.ukma.cinemax.exception;

public class InvalidIDException extends RuntimeException {
    public InvalidIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
