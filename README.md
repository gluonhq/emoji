[![Gluon](.github/assets/gluon_logo.svg)](https://gluonhq.com)

[![Build](https://github.com/gluonhq/emoji/actions/workflows/build.yml/badge.svg)](https://github.com/gluonhq/emoji/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.gluonhq/emoji)](https://search.maven.org/#search|ga|1|com.gluonhq.emoji)
[![License](https://img.shields.io/github/license/gluonhq/emoji)](https://opensource.org/licenses/GPL-3.0)

The GluonFX Emoji component provides Emoji support to JavaFX applications.

## Projects

### Emoji
Raw emoji support, no JavaFX control

### Emoji-Samples
Simple JavaFX application that displays all emojis

### Emoji-Updater
Utility to manually update Emoji to the latest emoji data, whenever a new version is released

## Build

Build the emoji artifact:

```
mvn clean install -f emoji
```

Use the emoji dependency in a Maven project:

```
<dependency>
    <groupId>com.gluonhq</groupId>
    <artifactId>emoji</artifactId>
    <version>${version}</version>
</dependency>
```

Run the sample application:

```
mvn javafx:run -f samples
```

Update the emoji list:

```
mvn javafx:run -f emoji-updater
```

## Contribution

All contributions are welcome!

There are two common ways to contribute:

- Submit [issues](https://github.com/gluonhq/emoji/issues) for bug reports, questions, or requests for enhancements.
- Contributions can be submitted via [pull request](https://github.com/gluonhq/emoji/pulls), provided you have signed the [Gluon Individual Contributor License Agreement (CLA)](https://cla.gluonhq.com).

Follow [contributing rules](https://github.com/gluonhq/emoji/blob/master/CONTRIBUTING.md) for this repository.
