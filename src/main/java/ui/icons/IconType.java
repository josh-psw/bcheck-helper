package ui.icons;

import burp.api.montoya.ui.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
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

        try (InputStream inputStream = IconFactory.class.getResourceAsStream(path)) {
            byte[] data = inputStream.readAllBytes();
            return new ImageIcon(data).getImage();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
