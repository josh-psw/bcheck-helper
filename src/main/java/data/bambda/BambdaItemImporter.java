package data.bambda;

import burp.api.montoya.bambda.BambdaImportResult;
import data.ItemImporter;
import logging.Logger;

public class BambdaItemImporter implements ItemImporter<Bambda> {
    private final Logger logger;
    private final burp.api.montoya.bambda.Bambda bambdaApi;

    public BambdaItemImporter(Logger logger, burp.api.montoya.bambda.Bambda bambdaApi) {
        this.logger = logger;
        this.bambdaApi = bambdaApi;
    }

    @Override
    public void importItem(Bambda item) {
        if (bambdaApi == null) {
            logger.logEventLogError("Bambda import unsupported in this version of Burp. Please upgrade to v2025.4+");
            throw new IllegalStateException("Bambda import is supported in v2025.4+");
        }

        BambdaImportResult importResult = bambdaApi.importBambda(item.content());

        if (!importResult.importErrors().isEmpty()) {
            StringBuilder sb = new StringBuilder("Error importing: ")
                    .append(item.name())
                    .append("\n");

            importResult.importErrors()
                    .forEach(s -> sb.append("\t").append(s).append("\n"));

            logger.logError(sb.toString());

            throw new IllegalStateException("Error importing item!");
        }
    }
}
