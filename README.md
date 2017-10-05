# Jdroid Java

## Continuous Integration
|Branch|Status|
| ------------- | ------------- |
|Master|[![Build Status](https://travis-ci.org/maxirosson/jdroid-java.svg?branch=master)](https://travis-ci.org/maxirosson/jdroid-java)|
|Staging|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-java.svg?branch=staging)](https://travis-ci.org/maxirosson/jdroid-java)|
|Production|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-java.svg?branch=production)](https://travis-ci.org/maxirosson/jdroid-java)|

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
### jdroid-java-firebase-dynamiclink
* Dynamic Link builder

## Setup

Add the following lines to your `build.gradle`:

    repositories {
      jcenter()
    }

    dependencies {
      compile 'com.jdroidframework:jdroid-java-core:X.Y.Z'
      compile 'com.jdroidframework:jdroid-java-firebase-database:X.Y.Z'
      compile 'com.jdroidframework:jdroid-java-firebase-dynamiclink:X.Y.Z'
    }

Replace the X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-java/releases/latest)


## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
