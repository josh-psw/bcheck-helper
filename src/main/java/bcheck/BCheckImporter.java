package bcheck;

import burp.api.montoya.scanner.bchecks.BCheckImportResult;
import burp.api.montoya.scanner.bchecks.BChecks;
import logging.Logger;

public interface BCheckImporter {
    void importBCheck(BCheck bCheck);

    class NullBCheckImporter implements BCheckImporter {
        @Override
        public void importBCheck(BCheck bCheck) {
        }
    }

    class DefaultBCheckImporter implements BCheckImporter {
        private final BChecks bChecks;
        private final Logger logger;

        public DefaultBCheckImporter(BChecks bChecks, Logger logger) {
            this.bChecks = bChecks;
            this.logger = logger;
        }

        @Override
        public void importBCheck(BCheck bCheck) {
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
