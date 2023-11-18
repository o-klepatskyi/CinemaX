package ua.edu.ukma.cinemax.commons.exception;

public class InvalidFilmDataException extends RuntimeException {
    public InvalidFilmDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
