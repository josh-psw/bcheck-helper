package ui.view.pane.settings;

import settings.repository.filesystem.FileSystemRepositorySettings;
import ui.view.component.filechooser.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Optional;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static ui.view.component.filechooser.ChooseMode.DIRECTORIES_ONLY;

class FileSystemRepositorySettingsSubComponent extends JPanel {

    FileSystemRepositorySettingsSubComponent(FileSystemRepositorySettings settings) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 20, 0, 20, 0};
        layout.rowHeights = new int[]{0, 5, 0};
        setLayout(layout);

        JLabel repositoryRootLabel = new JLabel("Repository root directory: ");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(5, 0, 0, 0);
        add(repositoryRootLabel, constraints);

        JTextField rootField = new JTextField();
        rootField.setText(settings.repositoryLocation());
        rootField.setEditable(false);
        rootField.setColumns(30);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(5, 0, 0, 0);
        add(rootField, constraints);

        JButton chooseRootButton = new JButton("Choose directory");

        chooseRootButton.addActionListener(e -> {
            Optional<Path> selectedDirectory = new FileChooser(DIRECTORIES_ONLY).prompt();

            selectedDirectory.ifPresent(file -> {
                String pathToDirectory = file.toAbsolutePath().toString();

                rootField.setText(pathToDirectory);
                settings.setRepositoryLocation(pathToDirectory);
            });
        });

        constraints = new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.insets = new Insets(5, 0, 0, 0);

        add(chooseRootButton, constraints);
    }
}
