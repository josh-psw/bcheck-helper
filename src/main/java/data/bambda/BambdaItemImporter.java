package data.bambda;

import data.ItemImporter;
import logging.Logger;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class BambdaItemImporter implements ItemImporter<Bambda> {
    private final Logger logger;
    private final Frame frame;

    public BambdaItemImporter(Logger logger, Frame frame) {
        this.logger = logger;
        this.frame = frame;
    }

    @Override
    public void importItem(Bambda item) {
        logger.logError("Direct Bambda imports are not supported yet.");
        JOptionPane.showMessageDialog(frame,
                "Bambda import not yet supported.\nRegister your interest for this functionality here: https://github.com/PortSwigger/burp-extensions-montoya-api/issues/112",
                "Unsupported action",
                WARNING_MESSAGE);
        throw new IllegalStateException("Bambda import not yet supported!");
    }
}
