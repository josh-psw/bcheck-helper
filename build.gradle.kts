plugins {
    id("java")
    id("checkstyle")
}

version = "3.1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.portswigger.burp.extensions:montoya-api:2025.5") // Min supported version: 2024.12
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-core:5.18.0")
    testImplementation("net.portswigger.burp.extensions:montoya-api:2025.5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

checkstyle {
    toolVersion = "10.12.4"
}

tasks.withType<Checkstyle> {
    reports {
        xml.required = false
        html.required = true
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveVersion.set(version.toString())
    
    from(rootDir) {
        include("LICENSE")
        into("META-INF")
    }   
}
