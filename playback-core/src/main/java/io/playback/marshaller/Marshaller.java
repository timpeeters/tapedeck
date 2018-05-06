package io.playback.marshaller;

import io.playback.Recording;

import java.io.Reader;
import java.io.Writer;

public interface Marshaller {
    void marshal(Recording recording, Writer writer);

    Recording unmarshal(Reader reader);
}
