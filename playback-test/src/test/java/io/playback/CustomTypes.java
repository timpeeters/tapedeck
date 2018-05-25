package io.playback;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;

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
                "\\w+",
                URI.class,
                URI::create
        );
    }
}
