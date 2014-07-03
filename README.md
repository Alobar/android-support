android-support
===============

Android java source code of package `com.alobarproductions.support`.

Class ObservableContentCompat
-----------------------------
Helper class for accessing features in `android.database.ContentObservable` introduced in Android API level 16 in a backwards compatible fashion.

API level 16 deprecates methods:

* `dispatchChange(boolean selfChange)`
* `notifyChange(boolean selfChange)`,

and introduces:

* `dispatchChange(boolean selfChange, Uri uri)`.

This leaves you you with nothing to call if you need to support lower API levels. Class ObservableContentCompat provides the necessary compatibility functionality.