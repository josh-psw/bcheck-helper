package data.bambda;

import settings.tags.TagColors;

import java.awt.*;
import java.util.Optional;

public class BambdaTagColors implements TagColors {
    @Override
    public Optional<Color> tagColor(String tag) {
        return Optional.empty();
    }
}