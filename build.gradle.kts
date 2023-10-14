plugins {
    id("java")
    id("maven-publish")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
  repositories {
    maven {
      name = "GitHubPackages"
      url = "https://maven.pkg.github.com/portswigger-josh/bcheck-helper"
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

tasks.test {
    useJUnitPlatform()
}
