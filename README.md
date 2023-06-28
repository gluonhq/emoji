Emoji Support
====

The GluonFX Emoji component provides Emoji support to JavaFX applications.

Emoji
---------
Raw emoji support, no JavaFX control

Emoji-Samples
---------
Simple JavaFX application that displays all emojis

Emoji-Updater
---------
Utility to manually update Emoji to the latest emoji data, whenever a new version is released

Build
=====

To update the emoji list:

```
cd emoji-updater
mvn javafx:run
cd ..
```

To build the Emoji artifact:

```
cd emoji
mvn clean install
cd ..
```

To run the sample application:


```
cd samples
mvn javafx:run
cd ..
```

Contribution
=====

All contributions are welcome!

There are two common ways to contribute:

- Submit [issues](https://github.com/gluonhq/emoji/issues) for bug reports, questions, or requests for enhancements.
- Contributions can be submitted via [pull request](https://github.com/gluonhq/emoji/pulls), provided you have signed the [Gluon Individual Contributor License Agreement (CLA)](https://cla.gluonhq.com).

Follow [contributing rules](https://github.com/gluonhq/emoji/blob/master/CONTRIBUTING.md) for this repository.
