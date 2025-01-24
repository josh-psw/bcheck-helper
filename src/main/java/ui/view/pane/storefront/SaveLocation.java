package ui.view.pane.storefront;

import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.view.component.filechooser.ChooseMode;
import ui.view.component.filechooser.FileChooser;

import java.nio.file.Path;
import java.util.Optional;

import static ui.view.component.filechooser.ChooseMode.SAVE_DIRECTORIES_ONLY;

public class SaveLocation {
    private final DefaultSaveLocationSettingsReader saveLocationSettingsReader;

    public SaveLocation(DefaultSaveLocationSettingsReader saveLocationSettingsReader) {
        this.saveLocationSettingsReader = saveLocationSettingsReader;
    }

    public Optional<Path> find() {
        return find(SAVE_DIRECTORIES_ONLY, null);
    }

    public Optional<Path> find(ChooseMode chooseMode, String defaultFileName) {
        return saveLocationSettingsReader
                .defaultSaveLocation()
                .or(() -> new FileChooser(chooseMode).prompt(defaultFileName));
    }
}
