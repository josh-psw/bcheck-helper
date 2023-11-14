package ui.view.pane.storefront;

import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.view.component.filechooser.ChooseMode;

import java.nio.file.Path;
import java.util.Optional;

import static ui.view.component.filechooser.ChooseMode.DIRECTORIES_ONLY;
import static ui.view.component.filechooser.FileChooser.withChooseMode;

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
                .or(() -> withChooseMode(chooseMode).prompt(defaultFileName));
    }
}
