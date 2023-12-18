package bcheck;

import bcheck.BCheckImporter.DefaultBCheckImporter;
import bcheck.BCheckImporter.NullBCheckImporter;
import burp.Burp;
import burp.api.montoya.MontoyaApi;
import logging.Logger;

import static burp.Burp.Capability.BCHECK_IMPORT;

public class BCheckImporterFactory {
    public static BCheckImporter from(MontoyaApi api, Burp burp, Logger logger) {
        return burp.hasCapability(BCHECK_IMPORT)
                ? new DefaultBCheckImporter(api.scanner().bChecks(), logger)
                : new NullBCheckImporter();
    }
}
