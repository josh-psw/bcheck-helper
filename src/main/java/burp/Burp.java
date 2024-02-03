package burp;

import burp.api.montoya.core.Version;

public class Burp {
    public enum Capability {
        BCHECK_IMPORT(20231201000025776L),
        TLS_VERIFICATION(20240101000026681L);

        private final long minimumSupportedBuildNumber;

        Capability(long minimumSupportedBuildNumber) {
            this.minimumSupportedBuildNumber = minimumSupportedBuildNumber;
        }
    }

    private final Version version;

    public Burp(Version version) {
        this.version = version;
    }

    public boolean hasCapability(Capability capability) {
        return version.buildNumber() >= capability.minimumSupportedBuildNumber;
    }
}
