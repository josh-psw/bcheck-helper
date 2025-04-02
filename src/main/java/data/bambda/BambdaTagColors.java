package data.bambda;

import settings.tags.TagColors;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static java.awt.Color.*;

public class BambdaTagColors implements TagColors {
    private static final Map<String, Color> COLORS = Map.of(
            "view filter", YELLOW,
            "match and replace request", CYAN,
            "match and replace response", GREEN,
            "custom column", MAGENTA,
            "custom action", ORANGE
    );

    @Override
    public Optional<Color> tagColor(String tag) {
        return Optional.ofNullable(COLORS.get(tag));
    }
}