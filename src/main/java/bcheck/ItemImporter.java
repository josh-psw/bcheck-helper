package bcheck;

import burp.api.montoya.scanner.bchecks.BCheckImportResult;
import burp.api.montoya.scanner.bchecks.BChecks;
import logging.Logger;

public interface ItemImporter<T> {
    void importItem(T item);

    class BCheckItemImporter implements ItemImporter<BCheck> {
        private final BChecks bChecks;
        private final Logger logger;

        public BCheckItemImporter(BChecks bChecks, Logger logger) {
            this.bChecks = bChecks;
            this.logger = logger;
        }

        @Override
        public void importItem(BCheck bCheck) {
            BCheckImportResult importResult = bChecks.importBCheck(bCheck.script(), true);

            if (!importResult.importErrors().isEmpty()) {
                StringBuilder sb = new StringBuilder("Error importing: ")
                        .append(bCheck.name())
                        .append("\n");

                importResult.importErrors()
                        .forEach(s -> sb.append("\t").append(s).append("\n"));

                logger.logError(sb.toString());

                throw new IllegalStateException("Error importing BCheck!");
            }
        }
    }
}
