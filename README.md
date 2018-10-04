# SweetPreferences [![Build Status](https://www.travis-ci.org/liip/SweetPreferences.svg?branch=master)](https://www.travis-ci.org/liip/SweetPreferences)

Add syntactic sugar to your Android Preferences

## Usage

```kotlin
// Define a class that will hold the preferences
class UserPreferences(sweetPreferences: SweetPreferences) {
    var counter: Int by sweetPreferences.delegate(0) // Default key is "counter"
    var username: String? by sweetPreferences.delegate(null, "usernameKey") // Key is hardcoded
}

// Obtain a SweetPreferences instance with default SharedPreferences
val sweetPreferences = SweetPreferences.Builder().withDefaultSharedPreferences(context).build()

// Build a UserPreferences instance
val preferences = UserPreferences(sweetPreferences)

// Use the preferences in a type-safe manner
preferences.username = "John Doe"
preferences.counter = 34
```

### Custom SharedPreference

If you have a custom `SharedPreference` instance, you can pass it to the `SweetPreferences` builder:

```kotlin
val customPreference = ... // Obtain custom SharedPreferences
val sweetPreferences = SweetPreferences.Builder().with(customPreference).build()
```

### Advanced methods

Other than accessing your preferences as Kotlin properties, you can also do the same as with a regular `SharedPreferences` or `SharedPreferences.Editor` object.

```kotlin
sweetPreferences.set("key", 1337)
sweetPreferences.remove("key")
sweetPreferences.contains("key")
sweetPreferences.get("key", 0)
sweetPreferences.clear() // Clear all preferences
```

### Demo app

You can check the demo Android application to see it in action.

## Setup

In your root *build.gradle* at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In your app *build.gradle*, add the dependency:

```gradle
dependencies {
    implementation 'com.github.liip:SweetPreferences:1.0.0'
}
```
