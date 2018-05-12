package io.playback;

import cucumber.api.java8.En;

import java.net.URI;

public class PlaybackStepDef implements En {
    public PlaybackStepDef(World world) {
        Given("^I create a new Playback instance$", () ->
                world.playback = Playback.configure(Configuration.builder()
                        .httpClient(world.httpClient)
                        .recordingRepository(world.recordingRepository)));

        When("^I receive a (\\w+) (.*) request$", (String method, String uri) ->
                world.response = world.playback.play(Request.builder()
                        .method(RequestMethod.valueOf(method))
                        .uri(URI.create(uri))
                        .build()));
    }
}
