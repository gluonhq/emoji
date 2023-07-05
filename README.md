[![Gluon](.github/assets/gluon_logo.svg)](https://gluonhq.com)

[![Build](https://github.com/gluonhq/emoji/actions/workflows/build.yml/badge.svg)](https://github.com/gluonhq/emoji/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.gluonhq/emoji)](https://search.maven.org/#search|ga|1|com.gluonhq.emoji)
[![License](https://img.shields.io/github/license/gluonhq/emoji)](https://opensource.org/licenses/GPL-3.0)

Emoji library is a Java implementation of the [emoji-data](https://github.com/iamcal/emoji-data) project adding emoji support for JavaFX applications.

## What is an Emoji?

From [Wikipedia](https://en.wikipedia.org/wiki/Emoji):

> An emoji is a pictogram, logogram, ideogram or smiley embedded in text and used in electronic messages and web pages.
> The primary function of emoji is to fill in emotional cues otherwise missing from typed conversation.

## Emoji support in JavaFX

JavaFX can be used to create client application that target desktop, mobiles and even web.
Given the wide variety of application, it becomes very important to have emoji support in the platform.
This project aims to bridge this gap and add emoji support to JavaFX.

## Usage

As stated earlier, emojis can be represented in form of text or a picture.
This library can be used to represent an Emoji as one of the following and seamlessly interchange between these types:

* String
* Unicode
* Image

[Emoji](https://github.com/gluonhq/emoji/blob/main/emoji/src/main/java/com/gluonhq/emoji/Emoji.java) data class is used to represent each emoji.
For better understanding of the Emoji class, we suggest you to go through [Using the data](https://github.com/iamcal/emoji-data#using-the-data) in emoji-data or the Emoji javadoc.

### API overview

`EmojiData` contains the public API which can be used to create `Emoji` objects from String and Unicode, and vice-versa.

Fetch `👋` emoji from text:

```
Optional<Emoji> emoji = emojiFromShortName("wave");
```

In case the exact text for emoji is not known, `search` API can be used to get a list of emojis which matches the text:

```
List<Emoji> emojis = search("wav");
```

Fetch `👋` emoji from unicode, e.g. `\uD83D\uDC4B`:

```
Optional<Emoji> emoji = EmojiData.emojiFromUnicodeString("\uD83D\uDC4B");
```

Fetch `👋` emoji from hex-code point string, e.g. `1F44B-1F3FC`:

```
Optional<Emoji> emoji = EmojiData.emojiFromCodepoints("1F44B-1F3FC");
```

Fetch `👋` emoji's unicode from text:

```
Optional<String> unicode = emojiForText("wave");
```

## Modules

The GitHub repository contains 3 modules: emoji, samples, emoji-updater. 

### emoji
Raw emoji support with `Emoji` and `EmojiData` classes.
This module is to be used  as a dependency in your JavaFX application.

Use the emoji dependency in a Maven project:

```
<dependency>
    <groupId>com.gluonhq</groupId>
    <artifactId>emoji</artifactId>
    <version>${version}</version>
</dependency>
```

Manually build the emoji artifact:

```
mvn clean install -f emoji
```


### samples
Simple JavaFX application that displays all emojis.
This application can be considered as a playground to test emoji and its APIs.

![emoji-sample.png](.github/assets/emoji-sample.png)

Each emoji is an ImageView and shows the description of the emoji on hover:

![emoji-sample-hover.png](.github/assets/emoji-sample-hover.png)

Run the sample application:

```
mvn javafx:run -f samples
```

### emoji-updater
Utility to manually update Emoji to the latest emoji data, whenever a new version is released.
Normally, this will be used by the library developers, but in case we are falling behind, you can use it too ;)

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
