package ui.view.component.filechooser;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.System.getProperty;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class FileChooser {
    private final ChooseMode chooseMode;

    public FileChooser(ChooseMode chooseMode) {
        this.chooseMode = chooseMode;
    }

    public Optional<Path> prompt() {
        return prompt(null);
    }

    public Optional<Path> prompt(String defaultFileName) {
        JFileChooser fileChooser = new JFileChooser();

        if (defaultFileName != null) {
            String homeDirectory = getProperty("user.home");
            Path defaultPath = Path.of(homeDirectory).resolve(defaultFileName);

            fileChooser.setSelectedFile(defaultPath.toFile());
        }

        return chooseMode.showDialog(fileChooser) == APPROVE_OPTION
                ? Optional.of(fileChooser.getSelectedFile().toPath().toAbsolutePath())
                : Optional.empty();
    }
}
