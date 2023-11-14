package ui.view.component.filechooser;

import file.system.view.CustomFileSystemView;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.System.getProperty;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class FileChooser {
    private final ChooseMode chooseMode;
    private final CustomFileSystemView fileSystemView;

    private FileChooser(ChooseMode chooseMode, CustomFileSystemView fileSystemView) {
        this.chooseMode = chooseMode;
        this.fileSystemView = fileSystemView;
    }

    public static FileChooser withChooseMode(ChooseMode chooseMode) {
        return new FileChooser(chooseMode, null);
    }

    public static FileChooser withChooseModeAndCustomFileSystemView(ChooseMode chooseMode, CustomFileSystemView fileSystemView) {
        return new FileChooser(chooseMode, fileSystemView);
    }

    public Optional<Path> prompt() {
        return prompt(null);
    }

    public Optional<Path> prompt(String defaultFileName) {
        JFileChooser fileChooser;

        if (fileSystemView == null) {
            fileChooser = new JFileChooser();
        } else {
            fileChooser = new JFileChooser(new File(fileSystemView.root()), fileSystemView);
        }

        fileChooser.setFileSelectionMode(chooseMode.jFileChooserMode);

        if (defaultFileName != null) {
            String homeDirectory = getProperty("user.home");
            Path defaultPath = Path.of(homeDirectory).resolve(defaultFileName);

            fileChooser.setSelectedFile(defaultPath.toFile());
        }

        int response = fileChooser.showSaveDialog(null);

        if (response == APPROVE_OPTION) {
            return Optional.of(fileChooser.getSelectedFile().toPath().toAbsolutePath());
        }

        return Optional.empty();
    }
}
