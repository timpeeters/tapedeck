package io.playback.marshaller;

import io.playback.Recording;
import io.playback.Request;
import io.playback.Response;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class YamlMarshallerTest {
    private final YamlMarshaller marshaller = new YamlMarshaller();

    @Test
    void marshalAndUnmarshal() {
        Recording recording = new Recording(Request.get().build(), Response.ok().build());

        StringWriter writer = new StringWriter();

        marshaller.marshal(recording, writer);

        assertThat(marshaller.unmarshal(new StringReader(writer.toString())))
                .isEqualToComparingFieldByFieldRecursively(recording);
    }
}
