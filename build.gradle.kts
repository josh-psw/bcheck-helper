plugins {
    id("java")
    id("checkstyle")
}

version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.portswigger.burp.extensions:montoya-api:2025.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-core:5.15.2")
    testImplementation("net.portswigger.burp.extensions:montoya-api:2025.2")
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