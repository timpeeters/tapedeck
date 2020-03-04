package io.playback.marshaller;

import io.playback.Recording;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
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
        LoaderOptions loaderOpts = loaderOptions();
        Yaml yaml = new Yaml(new Constructor(loaderOpts), representer(), dumperOptions(), loaderOpts);
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }

    private Representer representer() {
        Representer representer = new Representer();
        representer.addClassTag(YamlRecording.class, Tag.MAP);

        return representer;
    }

    private DumperOptions dumperOptions() {
        DumperOptions opts = new DumperOptions();
        opts.setDefaultFlowStyle(BLOCK);
        opts.setWidth(256);

        return opts;
    }

    private LoaderOptions loaderOptions() {
        LoaderOptions opts = new LoaderOptions();
        opts.setAllowRecursiveKeys(false);

        return opts;
    }
}
