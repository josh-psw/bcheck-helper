package ui.view.pane.storefront;

import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.view.component.filechooser.ChooseMode;
import ui.view.component.filechooser.FileChooser;

import java.nio.file.Path;
import java.util.Optional;

import static ui.view.component.filechooser.ChooseMode.DIRECTORIES_ONLY;

class SaveLocation {
    private final DefaultSaveLocationSettingsReader saveLocationSettingsReader;

    SaveLocation(DefaultSaveLocationSettingsReader saveLocationSettingsReader) {
        this.saveLocationSettingsReader = saveLocationSettingsReader;
    }

    Optional<Path> find() {
        return find(DIRECTORIES_ONLY, null);
    }

    Optional<Path> find(ChooseMode chooseMode, String defaultFileName) {
        return saveLocationSettingsReader
                .defaultSaveLocation()
                .or(() -> new FileChooser(chooseMode).prompt(defaultFileName));
    }
}
