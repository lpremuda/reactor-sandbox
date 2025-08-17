# Maven to Gradle Migration

This document describes the conversion of the Maven `pom.xml` to Gradle build files.

## Files Created

### Core Gradle Files
- `build.gradle` - Main build script using Groovy DSL
- `settings.gradle` - Project settings
- `gradle.properties` - Project properties and versions
- `gradle/wrapper/gradle-wrapper.properties` - Gradle wrapper configuration
- `gradle/wrapper/gradle-wrapper.jar` - Gradle wrapper JAR
- `gradlew` - Gradle wrapper script for Unix/Linux/macOS
- `gradlew.bat` - Gradle wrapper script for Windows

## Key Conversions

### Project Information
| Maven | Gradle |
|-------|--------|
| `<groupId>org.example</groupId>` | `group = "org.example"` |
| `<artifactId>reactor-sandbox</artifactId>` | `rootProject.name = "reactor-sandbox"` |
| `<version>1.0-SNAPSHOT</version>` | `version = "1.0-SNAPSHOT"` |

### Dependencies
| Maven | Gradle |
|-------|--------|
| `<dependency>` blocks | `implementation()`, `testImplementation()` |
| `<dependencyManagement>` with BOM | `implementation(platform())` |
| `<scope>test</scope>` | `testImplementation()` |

### Plugins
| Maven Plugin | Gradle Plugin |
|--------------|---------------|
| `kotlin-maven-plugin` | `kotlin("jvm")` |
| `kotlin-maven-allopen` | `kotlin("plugin.spring")` |
| `ktlint-maven-plugin` | `id("org.jlleitschuh.gradle.ktlint")` |
| `exec-maven-plugin` | Custom `Exec` task |
| `maven-dependency-plugin` | Custom `Copy` task |

### Build Configuration
| Maven | Gradle |
|-------|--------|
| `<sourceDirectory>` | `sourceSets.main.kotlin.setSrcDirs()` |
| `<testSourceDirectory>` | `sourceSets.test.kotlin.setSrcDirs()` |
| `<finalName>` | `tasks.jar.archiveBaseName.set()` |
| `<maven.compiler.source>` | `java.sourceCompatibility` |
| `<maven.compiler.target>` | `java.targetCompatibility` |

## Key Features Preserved

1. **Java 17 compatibility** - Set in both `java` block and Kotlin compiler options
2. **Kotlin 2.0.20** - Latest Kotlin version with Spring plugin support
3. **Project Reactor dependencies** - Including BOM for version management
4. **Ktlint integration** - Code style checking with same configuration
5. **Custom source directories** - Kotlin sources in `src/main/kotlin` and `src/test/kotlin`
6. **JAR manifest configuration** - Implementation entries for thin JAR launching
7. **Native dependency copying** - For DynamoDBLocal support
8. **OpenAPI linting task** - Custom exec task (currently skipped)

## Gradle Advantages

1. **Faster builds** - Incremental compilation and build caching
2. **Better dependency management** - More flexible dependency resolution
3. **Groovy DSL** - Familiar and concise build scripts
4. **Parallel execution** - Enabled by default in gradle.properties
5. **Modern tooling** - Better integration with IDEs and CI/CD

## Usage

### Common Commands
```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run ktlint checks
./gradlew ktlintCheck

# Format code with ktlint
./gradlew ktlintFormat

# Clean build
./gradlew clean

# Show dependencies
./gradlew dependencies
```

### IDE Integration
Most modern IDEs (IntelliJ IDEA, VS Code, Eclipse) have excellent Gradle support and will automatically import the project configuration.

## Migration Notes

1. **Property references** - Gradle properties are accessed differently than Maven properties
2. **Plugin syntax** - Gradle uses a more concise plugin DSL
3. **Task dependencies** - Gradle automatically handles most task dependencies
4. **Build lifecycle** - Gradle has a different but more flexible build lifecycle than Maven
5. **Java version compatibility** - Use Java 17 or Java 21 for best compatibility with Gradle 8.11. Java 24 is not yet fully supported.

## Java Version Setup

If you encounter Java version compatibility issues, set JAVA_HOME to use Java 17:

```bash
# macOS with Amazon Corretto
export JAVA_HOME=/Library/Java/JavaVirtualMachines/amazon-corretto-17.jdk/Contents/Home

# Or use SDKMAN to manage Java versions
sdk use java 17.0.x-amzn
```

The converted Gradle build maintains all the functionality of the original Maven build while providing better performance and developer experience.
