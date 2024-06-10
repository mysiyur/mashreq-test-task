package org.example;

import org.example.dto.EmployeeEntry;
import org.example.dto.RequestCategoryEntry;
import org.example.dto.RequestCategoryRepository;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.util.UriComponentsBuilder.fromPath;

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

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RequestCategoryRepository requestCategoryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected UUID workRemotelyId;
    protected UUID annualLeaveId;
    protected UUID sickLeaveId;

    @BeforeEach
    public void init() {
        workRemotelyId = requestCategoryRepository.findByName("WORK_REMOTELY").get().id();
        annualLeaveId = requestCategoryRepository.findByName("ANNUAL_LEAVE").get().id();
        sickLeaveId = requestCategoryRepository.findByName("SICK_LEAVE").get().id();
    }

    protected UriComponentsBuilder basePath() {
        return UriComponentsBuilder.newInstance()
                .host("localhost")
                .port(port)
                .scheme("http");
    }

    public EmployeeEntry createUser(EmployeeEntry employeeEntry) {
        final var uuid = employeeService.create(employeeEntry);
        return employeeService.findById(uuid).get();
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
