package ua.edu.ukma.cinemax.grpc;

import com.example.grpc.TestServiceGrpc;
import com.example.grpc.TestServiceProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class TestClient {
    @GrpcClient("grpc-server")
    private TestServiceGrpc.TestServiceBlockingStub testServiceBlockingStub;

    public TestServiceProto.TestResponse testMethod(String data, String index) {
        TestServiceProto.TestRequest testRequest = TestServiceProto.TestRequest.newBuilder()
                .setData(data)
                .setIndex(index)
                .build();
        TestServiceProto.TestResponse testResponse = this.testServiceBlockingStub.testMethod(testRequest);
        System.out.println(testResponse);
        return testResponse;
    }

    public Iterator<TestServiceProto.TestResponse> testMethodStream(String data, String index) {
        TestServiceProto.TestRequest testRequest = TestServiceProto.TestRequest.newBuilder()
                .setData(data)
                .setIndex(index)
                .build();
        return this.testServiceBlockingStub.testMethodStream(testRequest);
    }
}