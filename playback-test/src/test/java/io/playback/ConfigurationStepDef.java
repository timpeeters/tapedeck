package io.playback;

import cucumber.api.java8.En;
import io.playback.client.HttpClient;
import io.playback.repository.InMemoryRecordingRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfigurationStepDef implements En {
    public ConfigurationStepDef(World world) {
        Given("an in-memory recording repository", () ->
                world.recordingRepository = new InMemoryRecordingRepository());

        Given("a mock http client", () ->
                world.httpClient = mock(HttpClient.class));

        Given("^the http client is configured to return (\\w+) (\\w+)$", (String statusCode, String statusText) ->
                when(world.httpClient.execute(any(Request.class))).thenReturn(Response.builder()
                        .statusCode(statusCode)
                        .statusText(statusText)
                        .build()));

        Then("^the http client received (\\d+) requests?$", (Integer requestCount) ->
                verify(world.httpClient, times(requestCount)).execute(any(Request.class)));
    }
}
