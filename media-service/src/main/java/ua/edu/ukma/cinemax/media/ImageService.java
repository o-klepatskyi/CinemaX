package ua.edu.ukma.cinemax.media;

public interface ImageService {
    byte[] getFilmImageById(Long id);

    String getImageLink(Long id);
}
