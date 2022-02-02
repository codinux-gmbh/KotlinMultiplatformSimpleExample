
## Native app

Build app (TODO: find out why `./gradlew apps:NativeApp:linkReleaseExecutableNative` fails):
```shell
./gradlew apps:NativeApp:linkDebugExecutableNative
```

Run native app, e.g. with search term "Sparkasse":
```shell
./apps/NativeApp/build/bin/native/debugExecutable/BankFinder.kexe sparkasse
```