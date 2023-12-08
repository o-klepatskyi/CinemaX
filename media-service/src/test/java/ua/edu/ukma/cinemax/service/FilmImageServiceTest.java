package ua.edu.ukma.cinemax.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.ukma.cinemax.media.ImageService;
import ua.edu.ukma.cinemax.media.ImageServiceGrpc;

@SpringBootTest
public class FilmImageServiceTest {

    @Autowired
    private ImageService imageService;

    @GrpcClient("grpc-server-test")
    private ImageServiceGrpc.ImageServiceBlockingStub imageServiceBlockingStub;

    @Test
    public void testGetImageLink() {
        String url = imageService.getImageLink(1L);
        System.out.println(url);
    }
}
