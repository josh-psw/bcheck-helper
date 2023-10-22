package settings.tags;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.awt.Color.*;

public class TagColors {
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

    public Optional<Color> tagColor(String tag) {
        return Optional.ofNullable(COLORS.get(tag));
    }
}
