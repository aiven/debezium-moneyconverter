import com.diffplug.spotless.LineEnding

/*
 * Copyright 2025 Aiven Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  java
  distribution
  pmd
  id("com.diffplug.spotless") version "7.0.2"
  id("com.github.spotbugs") version "6.1.7"
}

repositories {
  mavenLocal()
  mavenCentral()
}

tasks.named<Test>("test") { useJUnitPlatform() }

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
  compileOnly(apache.kafka.connect.api)
  compileOnly(debezium.debezium.api)

  testImplementation(debezium.debezium.api)
  testImplementation(apache.kafka.connect.api)
  testImplementation(testinglibs.assertj)
  testImplementation(testinglibs.junit.jupiter)
}

pmd {
  isConsoleOutput = true
  toolVersion = "6.55.0"
  rulesMinimumPriority = 5

  tasks.pmdMain {
    ruleSetFiles = files("${project.rootDir}/gradle-config/aiven-pmd-ruleset.xml")
    ruleSets = ArrayList() // Clear the default rulesets
  }
  tasks.pmdTest {
    ruleSetFiles = files("${project.rootDir}/gradle-config/aiven-pmd-test-ruleset.xml")
    ruleSets = ArrayList() // Clear the default rulesets
  }
}

spotbugs {
  toolVersion = "4.8.3"
  excludeFilter.set(project.file("${project.rootDir}/gradle-config/spotbugs-exclude.xml"))

  tasks.spotbugsMain {
    reports.create("html") {
      enabled = true
      setStylesheet("fancy-hist.xsl")
    }
  }
  tasks.spotbugsTest {
    reports.create("html") {
      enabled = true
      setStylesheet("fancy-hist.xsl")
    }
  }
}

spotless {
  format("misc") {
    // define the files to apply `misc` to
    target("*.gradle", "*.md", ".gitignore", "**/META-INF/services/**")
    targetExclude(".*/**", "**/build/**", "**/.gradle/**")

    // define the steps to apply to those files
    trimTrailingWhitespace()
    leadingTabsToSpaces()
    endWithNewline()
    lineEndings = LineEnding.UNIX
  }

  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }

  java {
    licenseHeaderFile(file("${project.rootDir}/gradle-config/java.header"))
    importOrder("javax", "java", "org.apache.kafka", "io.aiven", "")
    removeUnusedImports()
    replaceRegex(
        "No wildcard imports.", "import(?:\\s+static)?\\s+[^\\*\\s]+\\*;(\r\n|\r|\n)", "$1")
    eclipse().configFile("${project.rootDir}/gradle-config/aiven-eclipse-formatter.xml")
    leadingTabsToSpaces()
    endWithNewline()
    trimTrailingWhitespace()
  }
}
