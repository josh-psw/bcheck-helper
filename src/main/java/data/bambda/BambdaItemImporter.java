package data.bambda;

import data.ItemImporter;
import logging.Logger;

public class BambdaItemImporter implements ItemImporter<Bambda> {
    private final Logger logger;

    public BambdaItemImporter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void importItem(Bambda item) {
        logger.logError("Direct Bambda imports are not supported yet.\nRegister your interest for this functionality here: https://github.com/PortSwigger/burp-extensions-montoya-api/issues/112");
        throw new IllegalStateException("Bambda import not yet supported!");
    }
}
