package ui.view.pane.settings;

import settings.defaultsavelocation.DefaultSaveLocationSettings;
import ui.view.component.HeaderLabel;
import ui.view.component.filechooser.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.Optional;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static ui.view.component.filechooser.ChooseMode.DIRECTORIES_ONLY;

class DefaultSaveLocationSettingsPanel extends JPanel {
    private final JLabel headerLabel = new HeaderLabel("Default save location for BChecks");
    private final JLabel descriptionLabel = new JLabel("Use this setting to define where BChecks should be saved to by default. If you don't use this setting, you'll be asked where to save the BCheck to every time that you save one");
    private final JButton chooseFileButton = new JButton("Choose directory");
    private final JCheckBox useSettingCheckbox = new JCheckBox("Enabled");

    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final JTextField pathField = new JTextField();

    DefaultSaveLocationSettingsPanel(DefaultSaveLocationSettings defaultSaveLocationSettings) {
        this.defaultSaveLocationSettings = defaultSaveLocationSettings;

        initialiseUi();
    }

    private void initialiseUi() {
        setupLayout();

        setupPathLabel();
        setupChooseFileButton();
        setupUseSettingCheckbox();

        addElements();
    }

    private void setupLayout() {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 20, 0};
        layout.rowHeights = new int[]{0, 10, 0, 5, 0, 5, 0};
        setLayout(layout);
    }

    private void setupPathLabel() {
        Optional<Path> potentialDefaultSaveLocation = defaultSaveLocationSettings.defaultSaveLocation();

        pathField.setText(potentialDefaultSaveLocation.map(Path::toString).orElse(""));
        pathField.setVisible(potentialDefaultSaveLocation.isPresent());
        pathField.setEditable(false);
        pathField.setColumns(30);
    }

    private void setupChooseFileButton() {
        Optional<Path> potentialDefaultSaveLocation = defaultSaveLocationSettings.defaultSaveLocation();
        useSettingCheckbox.setSelected(potentialDefaultSaveLocation.isPresent());

        chooseFileButton.setVisible(potentialDefaultSaveLocation.isPresent());
        chooseFileButton.addActionListener(e -> {
            if (e.getSource() == chooseFileButton) {
                Optional<Path> selectedDirectory = new FileChooser(DIRECTORIES_ONLY).prompt();

                selectedDirectory.ifPresent(file -> {
                    String pathToDirectory = file.toAbsolutePath().toString();

                    pathField.setText(pathToDirectory);
                    defaultSaveLocationSettings.setDefaultSaveLocation(Path.of(pathToDirectory));
                });
            }
        });
    }

    private void setupUseSettingCheckbox() {
        useSettingCheckbox.addActionListener(e -> {
            if (e.getSource() == useSettingCheckbox) {
                if (useSettingCheckbox.isSelected()) {
                    chooseFileButton.setVisible(true);
                    pathField.setVisible(true);

                    defaultSaveLocationSettings.setUseSetting(true);
                }
                else {
                    chooseFileButton.setVisible(false);
                    pathField.setVisible(false);

                    defaultSaveLocationSettings.setUseSetting(false);
                }
            }
        });
    }

    private void addElements() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        add(headerLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        constraints.weightx = 1.0;
        constraints.fill = HORIZONTAL;
        add(descriptionLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = FIRST_LINE_START;
        constraints.gridwidth = 3;
        add(useSettingCheckbox, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = FIRST_LINE_START;
        add(pathField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 6;
        constraints.anchor = FIRST_LINE_START;
        add(chooseFileButton, constraints);
    }
}
