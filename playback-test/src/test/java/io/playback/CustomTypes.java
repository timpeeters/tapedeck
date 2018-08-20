package io.playback;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

import java.net.URI;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomTypes implements TypeRegistryConfigurer {
    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineParameterType(requestMethodType());
        registry.defineParameterType(uriType());
        registry.defineDataTableType(recordingDataTableType());
    }

    private DataTableType recordingDataTableType() {
        return new DataTableType(Recording.class, (TableEntryTransformer<Recording>) entry ->
                new Recording(
                        Request.builder()
                                .method(RequestMethod.valueOf(entry.get("method")))
                                .uri(URI.create(entry.get("uri")))
                                .build(),
                        Response.builder()
                                .statusCode(entry.get("status code"))
                                .statusText(entry.get("status text"))
                                .build()));
    }

    private ParameterType<RequestMethod> requestMethodType() {
        return new ParameterType<>(
                "requestMethod",
                Arrays.stream(RequestMethod.values()).map(RequestMethod::toString).collect(Collectors.joining("|")),
                RequestMethod.class,
                (Transformer<RequestMethod>) RequestMethod::valueOf
        );
    }

    private ParameterType<URI> uriType() {
        return new ParameterType<>(
                "uri",
                ".*",
                URI.class,
                URI::create
        );
    }
}
