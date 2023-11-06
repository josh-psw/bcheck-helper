package ui.view.pane.submitter;

import event.EventListener;
import settings.github.GitHubSettingsReader;
import ui.controller.submission.SubmitterController;
import ui.view.component.HeaderLabel;
import ui.view.listener.SingleHandlerDocumentListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.concurrent.Executor;

import static event.EventKey.API_KEY_CHANGED;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.GridBagConstraints.*;
import static javax.swing.JOptionPane.*;
import static ui.controller.submission.SubmissionResult.REQUEST_FAILED;
import static ui.controller.submission.SubmissionResult.SUCCESS;

public class BCheckSubmitter extends JPanel {
    private static final String BCHECK_SUBMITTED_MESSAGE = "Your BCheck has been submitted. You can view the PR in the GitHub repo.";
    private static final String FAILED_TO_SUBMIT_BCHECK_MESSAGE = "Something went wrong when submitting your BCheck. Make sure your GitHub configuration is correct in the settings tab, or try again later.";
    private static final String CONFIRMATION_MESSAGE = "This will create a PR on the repo defined in the settings tab as the user defined by the API key. Do you want to continue?";
    private static final String CONFIRMATION_TITLE = "Confirm";
    private static final String INSTRUCTION_MESSAGE = "Type your BCheck below and click the 'Submit' button to submit your BCheck";
    private static final String AUTHENTICATION_MESSAGE = "You need to supply a GitHub API key in the 'Settings' tab to submit a BCheck.";
    private static final String SUBMIT_HEADER = "Submit BCheck";
    private static final String SUBMIT_BUTTON_TEXT = "Submit";

    private final GitHubSettingsReader gitHubSettingsReader;
    private final SubmitterController submitterController;
    private final Executor executor;

    private final JPanel titleArea = new JPanel();
    private final JLabel headerLabel = new HeaderLabel(SUBMIT_HEADER);
    private final JLabel instructionLabel = new JLabel(INSTRUCTION_MESSAGE);
    private final JLabel authenticationLabel = new HeaderLabel(AUTHENTICATION_MESSAGE);
    private final JButton submitButton = new JButton(SUBMIT_BUTTON_TEXT);
    private final JTextArea bCheckArea = new JTextArea();

    public BCheckSubmitter(
            GitHubSettingsReader gitHubSettingsReader,
            SubmitterController submitterController,
            EventListener eventListener,
            Executor executor) {
        super();

        this.gitHubSettingsReader = gitHubSettingsReader;
        this.submitterController = submitterController;
        this.executor = executor;

        initialiseUi();
        eventListener.listen(this::initialiseUi, API_KEY_CHANGED);
    }

    private void initialiseUi() {
        removeAll();

        if (gitHubSettingsReader.apiKey().isValid()) {
            setupSubmitUi();
        } else {
            setupAuthenticationNeededUi();
        }
    }

    private void setupSubmitUi() {
        initialiseTitleArea();
        initialiseTextArea();
        initialiseButton();

        setLayout(new BorderLayout());

        add(titleArea, NORTH);
        add(bCheckArea, CENTER);
        add(submitButton, SOUTH);
    }

    private void initialiseTextArea() {
        var monospacedFont = new Font(
                "monospaced",
                bCheckArea.getFont().getStyle(),
                bCheckArea.getFont().getSize()
        );

        var listener = new SingleHandlerDocumentListener(e -> submitButton.setEnabled(bCheckArea.getText() != null && !bCheckArea.getText().isBlank()));

        bCheckArea.setFont(monospacedFont);
        bCheckArea.getDocument().addDocumentListener(listener);
    }

    private void initialiseButton() {
        submitButton.setEnabled(false);

        submitButton.addActionListener(e -> {
            //todo: for some reason this is firing three times
            if (e.getSource() == submitButton) {
                executor.execute(() -> {
                    var confirmationResult = showConfirmDialog(null, CONFIRMATION_MESSAGE, CONFIRMATION_TITLE, OK_CANCEL_OPTION);

                    if (confirmationResult == OK_OPTION) {
                        var submissionResult = submitterController.submitBCheck(bCheckArea.getText());

                        if (submissionResult == SUCCESS) {
                            showMessageDialog(null, BCHECK_SUBMITTED_MESSAGE);
                        } else if (submissionResult == REQUEST_FAILED) {
                            showMessageDialog(null, FAILED_TO_SUBMIT_BCHECK_MESSAGE);
                        }
                    }
                });
            }
        });
    }

    private void setupAuthenticationNeededUi() {
        setLayout(new GridBagLayout());

        var constraints = new GridBagConstraints();
        constraints.anchor = BASELINE;

        add(authenticationLabel, constraints);
    }

    private void initialiseTitleArea() {
        titleArea.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.fill = HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        headerLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        titleArea.add(headerLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.fill = HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridy = 1;
        instructionLabel.setBorder(new EmptyBorder(10, 10, 10, 0));
        titleArea.add(instructionLabel, constraints);
    }
}
