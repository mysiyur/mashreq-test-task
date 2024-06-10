package org.example.holidays.nager;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.example.util.JsonUtil.listToJson;

public class NagerClient {

    private final HttpClient httpClient;

    public NagerClient() {
        httpClient = HttpClient.newHttpClient();
    }

    public List<NagerHoliday> getHolidays(String countryCode) {
        final var uri = UriComponentsBuilder.fromHttpUrl("https://date.nager.at/api/v3/NextPublicHolidays")
                .pathSegment(countryCode)
                .build().toUri();
        final var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        final HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.interrupted();
            throw new RuntimeException(e);
        }
        final var body = response.body();
        return listToJson(body, NagerHoliday.class);
    }
}
