package ua.edu.ukma.cinemax.service;

public interface ImageService {
    byte[] getFilmImageById(Long id);
    String getImageLink(Long id);
}
