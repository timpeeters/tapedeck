package io.playback;

import cucumber.api.java8.En;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseStepDef implements En {
    public ResponseStepDef(World world) {
        Then("the response is {word} {word}", (String statusCode, String statusText) ->
                assertThat(world.response).satisfies(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(statusCode);
                    assertThat(response.getStatusText()).isEqualTo(statusText);
                }));
    }
}
