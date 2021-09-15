package ru.digitalleague.adapter.registry;

import java.util.List;

public interface RegistryRenderer<T> {
    void renderRegistry(List<T> objects);
}
