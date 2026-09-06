import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
  java
  id("org.springframework.boot") version "4.1.0"
  id("com.github.spotbugs") version "6.4.8"
}

group = "clean.spring.study"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

val mockitoAgent: Configuration = configurations.create("mockitoAgent")

dependencies {
  implementation(platform(SpringBootPlugin.BOM_COORDINATES))  // Boot 플러그인과 같은 버전의 BOM
  annotationProcessor(platform(SpringBootPlugin.BOM_COORDINATES))
  testAnnotationProcessor(platform(SpringBootPlugin.BOM_COORDINATES))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.security:spring-security-core")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("com.mysql:mysql-connector-j")

  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  testCompileOnly("org.projectlombok:lombok")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test") // 신규
  testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
  testImplementation("org.instancio:instancio-core:6.0.0")
  mockitoAgent("org.mockito:mockito-core:5.23.0"){isTransitive = false}
}

tasks.withType<Test> {
  useJUnitPlatform()
  jvmArgs = listOf("-javaagent:${mockitoAgent.asPath}")
}

spotbugs {
  excludeFilter.set(file("${projectDir}/spotbugs-exclude-filter.xml"))
}

tasks.spotbugsTest {
  enabled = false
}
