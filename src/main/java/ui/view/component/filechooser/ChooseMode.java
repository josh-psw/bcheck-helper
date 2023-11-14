package ui.view.component.filechooser;

import javax.swing.*;

public enum ChooseMode {
    FILES_ONLY(JFileChooser.FILES_ONLY),
    DIRECTORIES_ONLY(JFileChooser.DIRECTORIES_ONLY),
    FILES_AND_DIRECTORIES(JFileChooser.FILES_AND_DIRECTORIES);

    public final int jFileChooserMode;

    ChooseMode(int jFileChooserMode) {
        this.jFileChooserMode = jFileChooserMode;
    }
}
