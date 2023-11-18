package ua.edu.ukma.cinemax.service;

import com.example.grpc.TestServiceGrpc;
import com.example.grpc.TestServiceProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestService extends TestServiceGrpc.TestServiceImplBase {

        @Override
        public void testMethod(TestServiceProto.TestRequest request, io.grpc.stub.StreamObserver<TestServiceProto.TestResponse> responseObserver) {
            if (request.getIndex().equals("error")) {
                responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("The index is error").asException());
                return;
            }

            final String message = "Hello, " + request.getData();

            System.out.println("Received request: " + request);
            TestServiceProto.TestResponse response = TestServiceProto.TestResponse.newBuilder().setResult(message).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

    @Override
    public void testMethodStream(TestServiceProto.TestRequest request, StreamObserver<TestServiceProto.TestResponse> responseObserver) {
        for (int i = 0; i < 10; i++) {
            final String message = "Hello, " + request.getData() + " " + i;
            TestServiceProto.TestResponse response = TestServiceProto.TestResponse.newBuilder().setResult(message).build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}
