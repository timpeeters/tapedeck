package io.playback;

import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;

import java.net.URI;

public class PlaybackStepDef implements En {
    public PlaybackStepDef(World world) {
        Given("I create a new Playback instance", () ->
                world.playback = Playback.configure(Configuration.builder()
                        .httpClient(world.httpClient)
                        .recordingRepository(world.recordingRepository)));

        When("I receive a {requestMethod} {uri} request", (RequestMethod method, URI uri) ->
                world.response = world.playback.play(Request.builder()
                        .method(method)
                        .uri(uri)
                        .build()));

        When("I receive a {requestMethod} {uri} request with the following request headers:",
                (RequestMethod method, URI uri, DataTable headers) ->
                        world.response = world.playback.play(Request.builder()
                                .method(method)
                                .uri(uri)
                                .headers(headers.<String, String> asMap(String.class, String.class).entrySet().stream()
                                        .map(e -> Header.header(e.getKey(), e.getValue()))
                                        .toArray(Header[]::new))
                                .build()));
    }
}
