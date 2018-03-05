package io.tapedeck;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RecorderTest {
    private static final String OK_200 = "200";

    private Recorder recorder;

    @Before
    public void initialize() {
        recorder = new Recorder();
    }

    @Test
    public void noMatchingRecording() {
        assertThatThrownBy(() -> recorder.replay(Request.get().build())).isInstanceOf(NoMatchingRecordingFound.class);
    }

    @Test
    public void noMatchingRecording_differentRequestMethod() {
        recorder.record(Request.get().build(), Response.ok().build());

        assertThatThrownBy(() -> recorder.replay(Request.post().build())).isInstanceOf(NoMatchingRecordingFound.class);
    }

    @Test
    public void noMatchingRecording_differentUri() {
        recorder.record(Request.get("/a").build(), Response.ok().build());

        assertThatThrownBy(() -> recorder.replay(Request.get("/b").build()))
                .isInstanceOf(NoMatchingRecordingFound.class);
    }

    @Test
    public void noMatchingRecording_differentAcceptHeader() {
        recorder.record(
                Request.get().header(Headers.ACCEPT, "application/json").build(),
                Response.ok().build());

        assertThatThrownBy(() -> recorder.replay(Request.get()
                .header(b -> b.withName(Headers.ACCEPT).withValue("application/xml"))
                .build()))
                .isInstanceOf(NoMatchingRecordingFound.class);
    }

    @Test
    public void noMatchingRecording_differentRequestBody() {
        recorder.record(
                Request.post().body("hello").build(),
                Response.ok().build());

        assertThatThrownBy(() -> recorder.replay(Request.post().body("world").build()))
                .isInstanceOf(NoMatchingRecordingFound.class);
    }

    @Test
    public void replay_statusCode() {
        recorder.record(Request.get().build(), Response.builder().statusCode(OK_200).build());

        assertThat(recorder.replay(Request.get().build())).satisfies(r ->
                assertThat(r.getStatusCode()).isEqualTo(OK_200));
    }

    @Test
    public void replay_body() {
        recorder.record(Request.get().build(), Response.builder().body("Hello world").build());

        assertThat(recorder.replay(Request.get().build())).satisfies(r ->
                assertThat(r.getBodyAsString()).isEqualTo("Hello world"));
    }

    @Test
    public void replay_bodyWithEncoding() {
        recorder.record(Request.get().build(), Response.builder()
                .header(Headers.CONTENT_TYPE, "text/plain;charset=iso-8859-1")
                .body("Hélicoptère".getBytes(Charset.forName("ISO-8859-1"))).build());

        assertThat(recorder.replay(Request.get().build())).satisfies(r ->
                assertThat(r.getBodyAsString()).isEqualTo("Hélicoptère"));
    }

    @Test
    public void replay_header() {
        recorder.record(
                Request.get().build(),
                Response.builder().header(Headers.CONTENT_TYPE, "application/json").build());

        assertThat(recorder.replay(Request.get().build())).satisfies(r ->
                assertThat(r.getHeader(Headers.CONTENT_TYPE)).containsExactly("application/json"));
    }
}
