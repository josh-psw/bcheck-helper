plugins {
    id("java")
    id("checkstyle")
}

version = "3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.portswigger.burp.extensions:montoya-api:2024.12")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-core:5.17.0")
    testImplementation("net.portswigger.burp.extensions:montoya-api:2024.12")

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
