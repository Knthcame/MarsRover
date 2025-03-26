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
- [Kover](https://github.com/Kotlin/kotlinx-kover): Code coverage analysis.

## Continuous integration

This project uses Github Actions to ensure that the quality meets the desired standard during development.

For this, the main branch has been protected, requiring a Pull Request with the following quality gates:
- All tests (unit & UI) are executed and shall pass.
- Static code analysis is performed via:
  - [SonarQube Cloud](https://sonarcloud.io/project/overview?id=Knthcame_MarsRover): This enforces sonar's [Clean as you code](https://docs.sonarsource.com/sonarqube-cloud/core-concepts/clean-as-you-code/introduction/) own quality gate.
  - [Android lint](https://developer.android.com/studio/write/lint): Implement specific lint checks for Android apps.
- A release apk is produced to enable manual testing.