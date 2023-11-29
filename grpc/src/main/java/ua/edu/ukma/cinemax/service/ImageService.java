package ua.edu.ukma.cinemax.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ua.edu.ukma.cinemax.media.ImageServiceGrpc;
import ua.edu.ukma.cinemax.media.ImageServiceProto;

@GrpcService
public class ImageService extends ImageServiceGrpc.ImageServiceImplBase {
    private static final String DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/6/65/No-Image-Placeholder.svg";

    @Override
    public void getImageMethod(
            ImageServiceProto.ImageRequest request,
            StreamObserver<ImageServiceProto.ImageResponse> responseObserver
    ) {
        String posterPath = request.getPath();
        String url = String.format("https://image.tmdb.org/t/p/w500%s", posterPath);

        ImageServiceProto.ImageResponse response = ImageServiceProto
                .ImageResponse
                .newBuilder()
                .setUrl(url)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getDefaultImageMethod(ImageServiceProto.DefaultImageRequest request, StreamObserver<ImageServiceProto.ImageResponse> responseObserver) {
        ImageServiceProto.ImageResponse response = ImageServiceProto
                .ImageResponse
                .newBuilder()
                .setUrl(DEFAULT_IMAGE)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
