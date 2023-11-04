package ui.icons;

import burp.api.montoya.ui.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;

import static java.util.Locale.US;

public enum IconType {
    CLOSE("close"), SEARCH("search");

    private final String name;

    IconType(String name) {
        this.name = name;
    }

    Image image(Theme theme) {
        String path = "/bcheck_helper/%s_%s.png".formatted(name, theme.name().toLowerCase(US));

        try {
            byte[] data = IconFactory.class.getResourceAsStream(path).readAllBytes();
            return new ImageIcon(data).getImage();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
