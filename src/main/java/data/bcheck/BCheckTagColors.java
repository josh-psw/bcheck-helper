package data.bcheck;

import settings.tags.TagColors;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.awt.Color.*;

public class BCheckTagColors implements TagColors {
    private static final Map<String, Color> COLORS = Map.of(
            "passive", YELLOW,
            "exposure", CYAN,
            "token", CYAN,
            "informative", GREEN,
            "actuator", MAGENTA,
            "springboot", ORANGE,
            "rce", PINK,
            "alibaba", RED
    );

    @Override
    public Optional<Color> tagColor(String tag) {
        return Optional.ofNullable(COLORS.get(tag));
    }
}