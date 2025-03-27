[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Knthcame_MarsRover&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Knthcame_MarsRover)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Knthcame_MarsRover&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Knthcame_MarsRover)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Knthcame_MarsRover&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Knthcame_MarsRover)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Knthcame_MarsRover&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Knthcame_MarsRover)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Knthcame_MarsRover&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Knthcame_MarsRover)

# MarsRover

"MarsRover" is a sample Android app built with Kotlin and Jetpack Compose.

The app allows to send instructions to the Mars Rover to turn or move in a specific direction. 
The robot attempts to move as indicated inside its "grid" and returns the final coordinates as result.

# Setup

In order to launch to project must be cloned locally and opened with Android Studio. Or other IDEs that support Android development.

# Tech stack

The app is developed with Kotlin as the programming language. It uses the following libraries:
- [Jetpack compose](https://developer.android.com/compose): for the user interface, based on Material3.
- [JUnit4](https://junit.org/junit4/): For unit & UI tests.
- [Mockk](https://github.com/mockk/mockk): For mocking dependencies in unit tests.
- [Kover](https://github.com/Kotlin/kotlinx-kover): Code coverage analysis.
- [Compose navigation](https://developer.android.com/develop/ui/compose/navigation): Navigation between screens.
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization): For (de)serialization of JSON files.
- [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle): To implement the MVVM architectural pattern.
- [Koin](https://github.com/InsertKoinIO/koin): Dependency injection.

## Continuous integration

This project uses Github Actions to ensure that the quality meets the desired standard during development.

For this, the main branch has been protected, requiring a Pull Request with the following quality gates:
- All tests (unit & UI) are executed and shall pass.
- Static code analysis is performed via:
  - [SonarQube Cloud](https://sonarcloud.io/project/overview?id=Knthcame_MarsRover): This enforces sonar's [Clean as you code](https://docs.sonarsource.com/sonarqube-cloud/core-concepts/clean-as-you-code/introduction/) own quality gate.
  - [Android lint](https://developer.android.com/studio/write/lint): Implement specific lint checks for Android apps.
- A release apk is produced to enable manual testing.

### Dependabot

This project has [Dependabot](https://docs.github.com/en/code-security/dependabot/working-with-dependabot) enabled so that the dependencies are kept up-to-date.
Dependabot scans for the new available versions weekly and raises PRs with the version updates. 

It also checks for vulnerabilities in the project dependencies.

# Possible improvements (TODOs)
- Improve input validation in setup screen. 
  - E.g: Make sure that X, Y coordinates do not exceed the plateau size. Visual feedback can be added via the supporting text of the corresponding TextField.
- Add unit tests for MovementsViewModel
  - Due to a current [limitation](https://issuetracker.google.com/issues/349807172) of the navigation library, SavedStateHandle requires to use instrumented tests.
  - Also, mocking SavedStateHandle requires some workarounds at the moment.
  - Possible solutions include changing the typed navigation for non-typed one.