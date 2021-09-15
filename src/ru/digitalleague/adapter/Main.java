package ru.digitalleague.adapter;

import ru.digitalleague.adapter.registry.RegistryRendererService;
import ru.digitalleague.adapter.registry.UserRegistryRenderer;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        new UserRegistryRenderer(new RegistryRendererService()).renderRegistry(List.of(
                new Mentor(1L, "Денис"),
                new Mentor(2L, "Александр"),
                new Mentor(3L, "Владимир"),
                new Mentor(4L, "Михаил")
        ));
    }
}
