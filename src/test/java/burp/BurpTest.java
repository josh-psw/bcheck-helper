package burp;

import burp.Burp.Capability;
import burp.api.montoya.core.Version;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static burp.Burp.Capability.BCHECK_IMPORT;
import static burp.Burp.Capability.TLS_VERIFICATION;
import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BurpTest {
    private final Version preferences = mock(Version.class);
    private final Burp burp = new Burp(preferences);

    private static Stream<Arguments> capabilityCheckData() {
        return Stream.of(
                arguments(BCHECK_IMPORT, 0, false),
                arguments(BCHECK_IMPORT, 20231201000025775L, false),
                arguments(BCHECK_IMPORT, 20231201000025776L, true),
                arguments(BCHECK_IMPORT, MAX_VALUE, true),
                arguments(TLS_VERIFICATION, 0, false),
                arguments(TLS_VERIFICATION, 20240101000026680L, false),
                arguments(TLS_VERIFICATION, 20240101000026681L, true),
                arguments(TLS_VERIFICATION, MAX_VALUE, true)
        );
    }

    @MethodSource("capabilityCheckData")
    @ParameterizedTest
    void givenVersion_whenCapabilityChecked_thenCorrectSupportReturned(Capability capability, long buildNumber, boolean capabilitySupported) {
        when(preferences.buildNumber()).thenReturn(buildNumber);

        assertThat(burp.hasCapability(capability)).isEqualTo(capabilitySupported);
    }
}