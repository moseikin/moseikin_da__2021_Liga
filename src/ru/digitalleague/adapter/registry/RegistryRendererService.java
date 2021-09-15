package ru.digitalleague.adapter.registry;

import java.util.List;
import java.util.stream.Collectors;

public class RegistryRendererService {

    public void renderRegistry(List<String> headers, List<List<String>> values) {
        System.out.printf("%s\n%s", renderRow(headers), values.stream().map(this::renderRow).collect(Collectors.joining("\n")));
    }

    private String renderRow(List<String> values) {
        return values.stream()
                .map(value -> String.format("%20s", value))
                .collect(Collectors.joining(", "));
    }
}
