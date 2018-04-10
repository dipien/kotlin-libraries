# Jdroid Java

## Continuous Integration
|Branch|Status|Workflows|Insights|
| ------------- | ------------- | ------------- | ------------- |
|master|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java/tree/master.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java/tree/master)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java/tree/master)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java/master)|
|staging|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java/tree/staging.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java/tree/staging)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java/tree/staging)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java/staging)|
|production|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java/tree/production.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java/tree/production)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java/tree/production)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java/production)|

## Features
### jdroid-java-core
* JSON marshallers
* Exception handling
* Repository Pattern
 * In Memory Implementation 
* Utilities for Collections, Strings, Dates, Files, Encryption, Threads, [Logging](http://www.slf4j.org/), Validations, Reflection and more
### jdroid-java-firebase-database
* Repository Pattern
 * [Firebase Implementation](https://www.firebase.com)

## Setup

Add the following lines to your `build.gradle`:

    repositories {
      jcenter()
    }

    dependencies {
      implementation 'com.jdroidtools:jdroid-java-core:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-firebase-database:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-remote-config:X.Y.Z'
    }

Replace the X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-java/releases/latest)


## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)
