package settings.defaultsavelocation;

import java.nio.file.Path;
import java.util.Optional;

public interface DefaultSaveLocationSettingsReader {
    Optional<Path> defaultSaveLocation();
}
