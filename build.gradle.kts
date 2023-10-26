plugins {
    id("java")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

publishing {
  publications {
    create<MavenPublication>("bcheck-helper") {
        from(components["java"])
    }
  }
  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/portswigger-josh/bcheck-helper")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}

dependencies {
    compileOnly("net.portswigger.burp.extensions:montoya-api:2023.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("net.portswigger.burp.extensions:montoya-api:2023.10.2")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.test {
    useJUnitPlatform()
}
