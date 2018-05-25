package io.playback;

import cucumber.api.java8.En;

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
    }
}
