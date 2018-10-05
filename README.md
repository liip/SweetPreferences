# SweetPreferences

[![Build Status](https://www.travis-ci.org/liip/SweetPreferences.svg?branch=master)](https://www.travis-ci.org/liip/SweetPreferences)
[![GitHub license](https://img.shields.io/github/license/liip/SweetPreferences.svg)](https://github.com/liip/SweetPreferences/blob/master/LICENSE)
[![Jitpack](https://jitpack.io/v/liip/SweetPreferences.svg)](https://jitpack.io/#liip/SweetPreferences)


Add syntactic sugar to your Android Preferences.

## Installation

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

## Usage

```kotlin
// Define a class that will hold the preferences
class UserPreferences(sweetPreferences: SweetPreferences) {
    // Default key is "counter"
    // Default value is "0"
    var counter: Int by sweetPreferences.delegate(0)
    
    // Key is hardcoded to "usernameKey"
    // Default value is null
    var username: String? by sweetPreferences.delegate(null, "usernameKey") 
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
