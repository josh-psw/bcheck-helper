package ui.view.pane.settings;

import repository.RepositoryType;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;
import settings.repository.github.GitHubSettings;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.FIRST_LINE_START;
import static java.awt.GridBagConstraints.HORIZONTAL;

class RepositorySettingsComponent implements SettingsComponent {
    private final JPanel component;
    private final RepositorySettings repositorySettings;
    private final GitHubSettings gitHubSettings;
    private final FileSystemRepositorySettings fileSystemRepositorySettings;

    private JComponent subComponent;

    RepositorySettingsComponent(RepositorySettings repositorySettings,
                                GitHubSettings gitHubSettings,
                                FileSystemRepositorySettings fileSystemRepositorySettings) {
        this.repositorySettings = repositorySettings;
        this.gitHubSettings = gitHubSettings;
        this.fileSystemRepositorySettings = fileSystemRepositorySettings;
        component = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 25, 0};
        layout.rowHeights = new int[]{0, 15, 0, 25};
        component.setLayout(layout);

        JLabel typeDescriptionLine = new JLabel("BChecks can either be loaded from a remote GitHub repository or from the filesystem.");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(20, 0, 0, 10);
        component.add(typeDescriptionLine, constraints);

        JLabel repositoryTypeNameDescription = new JLabel("Repository type:");

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 2;
        component.add(repositoryTypeNameDescription, constraints);

        JComboBox<RepositoryType> repositoryTypeComboBox = new JComboBox<>(RepositoryType.values());
        repositoryTypeComboBox.setSelectedItem(repositorySettings.repositoryType());
        repositoryTypeComboBox.addActionListener(e -> configureSubComponent((RepositoryType) repositoryTypeComboBox.getSelectedItem()));

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 2;
        component.add(repositoryTypeComboBox, constraints);

        configureSubComponent(repositorySettings.repositoryType());
    }

    private void configureSubComponent(RepositoryType repositoryType) {
        if (subComponent != null) {
            component.remove(subComponent);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1.0;
        constraints.gridwidth = 3;
        constraints.fill = HORIZONTAL;

        repositorySettings.setRepositoryType(repositoryType);

        subComponent = switch (repositoryType) {
            case GITHUB -> new GitHubRepositorySettingsSubComponent(gitHubSettings);
            case FILESYSTEM -> new FileSystemRepositorySettingsSubComponent(fileSystemRepositorySettings);
        };

        component.add(subComponent, constraints);
    }

    @Override
    public String title() {
        return "Repository configuration";
    }

    @Override
    public String description() {
        return "Use these settings to configure the repository used to load BChecks.  Once you've changed these settings, you will need to refresh the store for them to take effect.";
    }

    @Override
    public JComponent component() {
        return component;
    }
}
