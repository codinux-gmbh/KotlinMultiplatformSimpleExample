# KotlinMultiplatformSimpleExample

A simple application to show how Kotlin Multiplatform works and what you can do with it.

Contains a server app for the JVM and clients for Android, iOS, JavaScript, Linux, Windows and macOS.


## Start the JVM server

Go to file `Application.kt` in `common` -> `jvmMain` and click the run button next to the main() function.

TODO: Create an extra server app module.


## Android app

The Android SDK has to be installed.

IntelliJ / Android Studio then usually creates a run configuration the Android app. Simply press the run or debug button in IntelliJ then.

## Web app

Run in continuous development mode:

```shell
./gradlew apps:WebApp:run --continuous
```

As displayed in console output navigate then in a browser to `http://localhost:8081`.


## Native app

Build app (TODO: find out why `./gradlew apps:NativeApp:linkReleaseExecutableNative` fails):

```shell
./gradlew apps:NativeApp:linkDebugExecutableNative
```

Run native app, e.g. with search term "Sparkasse":

```shell
./apps/NativeApp/build/bin/native/debugExecutable/FaviconFinder.kexe heise.de
```