package io.playback.marshaller;

import io.playback.Recording;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.Reader;
import java.io.Writer;

import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

public class YamlMarshaller implements Marshaller {
    @Override
    public void marshal(Recording recording, Writer writer) {
        yaml().dump(YamlRecording.from(recording), writer);
    }

    @Override
    public Recording unmarshal(Reader reader) {
        return YamlRecording.map(yaml().loadAs(reader, YamlRecording.class));
    }

    private Yaml yaml() {
        Yaml yaml = new Yaml(representer(), dumperOptions());
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }

    private DumperOptions dumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(BLOCK);
        dumperOptions.setWidth(256);

        return dumperOptions;
    }

    private Representer representer() {
        Representer representer = new Representer();
        representer.addClassTag(YamlRecording.class, Tag.MAP);

        return representer;
    }
}
