package ui.view.pane.settings;

import settings.github.GitHubSettings;
import ui.view.component.HeaderLabel;
import ui.view.listener.SingleHandlerDocumentListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.FIRST_LINE_START;

class GitHubSettingsPanel extends JPanel {
    private final JLabel headerLabel = new HeaderLabel("GitHub configuration");
    private final JLabel descriptionLabelFirstLine = new JLabel("Use these settings to define which GitHub repo the extension looks at to find BChecks.");
    private final JLabel descriptionLabelSecondLine = new JLabel("If the repo isn't public, you'll need to specify an API key too. You can look at GitHub's documentation to find out how to create one.");
    private final JLabel descriptionLabelThirdLine = new JLabel("If you're using the same API key across multiple applications, you might exceed GitHub's rate limit, meaning that this extension will no longer work until the rate limit resets.");
    private final JLabel descriptionLabelFourthLine = new JLabel("Once you've changed these settings, you'll need to refresh the store for them to take effect.");
    private final JLabel repoNameDescription = new JLabel("Repo name (in owner/repo format e.g. portswigger/bchecks)");
    private final JLabel apiKeyDescription = new JLabel("API key (only needed if it is a private repo)");
    private final JTextField repoNameField = new JTextField();
    private final JTextField apiKeyField = new JTextField();

    private final GitHubSettings gitHubSettings;

    GitHubSettingsPanel(GitHubSettings gitHubSettings) {
        this.gitHubSettings = gitHubSettings;

        initialiseUi();
    }

    private void initialiseUi() {
        setupLayout();

        setupRepoNameField();
        setupApiKeyField();

        addElements();
    }

    private void setupLayout() {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 20, 0};
        layout.rowHeights = new int[]{0, 10, 0, 5, 0, 5, 0, 5, 0, 30, 0, 5, 0};

        setLayout(layout);
    }

    private void setupRepoNameField() {
        repoNameField.setText(gitHubSettings.repo());
        repoNameField.setColumns(25);
        repoNameField.getDocument().addDocumentListener(
                new SingleHandlerDocumentListener(e -> gitHubSettings.setRepo(repoNameField.getText()))
        );
    }

    private void setupApiKeyField() {
        apiKeyField.setText(gitHubSettings.apiKey());
        apiKeyField.setColumns(40);
        apiKeyField.getDocument().addDocumentListener(
                new SingleHandlerDocumentListener(e -> gitHubSettings.setApiKey(apiKeyField.getText()))
        );
    }

    private void addElements() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        add(headerLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        add(descriptionLabelFirstLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        add(descriptionLabelSecondLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 6;
        constraints.gridwidth = 3;
        add(descriptionLabelThirdLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 8;
        constraints.gridwidth = 3;
        add(descriptionLabelFourthLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 10;
        add(repoNameDescription, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 10;
        constraints.weightx = 1;
        add(repoNameField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 12;
        add(apiKeyDescription, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 12;
        add(apiKeyField, constraints);
    }
}
