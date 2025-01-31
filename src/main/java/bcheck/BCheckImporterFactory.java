package bcheck;

import bcheck.ItemImporter.BCheckItemImporter;
import bcheck.ItemImporter.NullItemImporter;
import burp.Burp;
import burp.api.montoya.MontoyaApi;
import logging.Logger;

import static burp.Burp.Capability.BCHECK_IMPORT;

public class BCheckImporterFactory {
    public static ItemImporter<BCheck> from(MontoyaApi api, Burp burp, Logger logger) {
        return burp.hasCapability(BCHECK_IMPORT)
                ? new BCheckItemImporter(api.scanner().bChecks(), logger)
                : new NullItemImporter<>();
    }
}
