package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public abstract class AbstractIntegrationTest {

    @LocalServerPort
    private String port;

    @BeforeEach
    public void init() {
    }

    protected UriComponentsBuilder basePath() {
        return UriComponentsBuilder.newInstance()
                .host("localhost")
                .port(port)
                .scheme("http");
    }

    protected HttpResponse<String> sendGet(URI uri) {
        try {
            return HttpClient.newHttpClient().send(HttpRequest.newBuilder(uri).GET().build(), BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpResponse<String> sendPost(URI uri, String payload) {
        try {
            final var request = HttpRequest.newBuilder(uri).POST(BodyPublishers.ofString(payload))
                    .header("Content-Type", "application/json")
                    .build();
            return HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
