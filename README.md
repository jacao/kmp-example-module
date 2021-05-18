# kmp-example-module

### Building iOS framework for testing

- Run `./gradlew createXCFramework` in the root of the project to build the framework in `./KMPExampleModule/swiftpackage`
- Or run `./gradlew packForXcode` in the root of the project to build the framework in `./KMPExampleModule/build/xcode-frameworks`

### Building iOS SPM package and publishing 

- Ensure the `version` is correct in `./gradle.properties`
- Run `./gradlew createSwiftPackage` in the root of the project to build the framework and the package config
- This will build all the required package files in `./KMPExampleModule/swiftpackage`
- Navigate into the `swiftpackage` folder - this is a git repo (https://github.com/jacao/KMPExampleModulePackage)
- Commit, tag and push any changes e.g:
```
git commit -m "Version 1.0.1"
git tag 1.0.1
git push origin master --tags
```
- This can now imported into a project using Swift Package Manager

### Helpful iOS Resources

* https://johnoreilly.dev/posts/kotlinmultiplatform-swift-package/
* https://github.com/ge-org/multiplatform-swiftpackage
* https://levelup.gitconnected.com/integrate-kotlin-multiplatform-mobile-to-existing-android-and-ios-apps-93876060bff5

