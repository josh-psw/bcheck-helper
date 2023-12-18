package ui.view.component.filechooser;

import javax.swing.*;

public enum ChooseMode {
    SAVE_FILES_ONLY(JFileChooser.FILES_ONLY),
    SAVE_DIRECTORIES_ONLY(JFileChooser.DIRECTORIES_ONLY),
    OPEN_DIRECTORIES_ONLY(JFileChooser.DIRECTORIES_ONLY);

    private final int jFileChooserMode;

    ChooseMode(int jFileChooserMode) {
        this.jFileChooserMode = jFileChooserMode;
    }

    int showDialog(JFileChooser fileChooser) {
        fileChooser.setFileSelectionMode(jFileChooserMode);

        return switch(this) {
            case SAVE_FILES_ONLY, SAVE_DIRECTORIES_ONLY -> fileChooser.showSaveDialog(null);
            case OPEN_DIRECTORIES_ONLY -> fileChooser.showOpenDialog(null);
        };
    }
}
