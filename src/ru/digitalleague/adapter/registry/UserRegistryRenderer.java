package ru.digitalleague.adapter.registry;

import ru.digitalleague.adapter.Mentor;

import java.util.List;
import java.util.stream.Collectors;

public class UserRegistryRenderer {
    private RegistryRendererService adaptee;

    public UserRegistryRenderer(RegistryRendererService adaptee) {
        this.adaptee = adaptee;
    }

    public void renderRegistry(List<Mentor> objects) {
        adaptee.renderRegistry(
                List.of("ID", "Full name"),
                objects.stream().map(mentor -> List.of(mentor.getId().toString(), mentor.getName())).collect(Collectors.toList())
        );
    }
}
