HvZDevelopment
==============
- - -

Introduction
------------

Version 1.3


An Android application to support the Georgia Tech Humans vs. Zombies game.

More information can be found at [HvZ](https://hvz.gatech.edu).

Directories
------------

* **HvZAndroidApplication/**

    An Eclipse project for the HvZ Android client.

* **actionbarsherlock/**

    An Eclipse project library that is required by our Android client.

* **HvZAPI/**

    A small PHP library that acts as a public API to the HvZ database.


Android Client
-------------
This project is currently being built with the following options:

* Min SDK: Android 2.3.3
* Target SDK: Android 4.2
* Build SDK: Android 4.2

### Libraries Used ###

* **ActionBarSherlock**
    
    For enhanced backwards compatibility and easy customization of the Android ActionBar.

* **GSON**
    
    For parsing JSON data from the webserver.

* **JSoup**

    For authenticating with the GaTech CAS servers.

* **zxing**

    For scanning 2-D barcodes.

### Installation ###

1.  Install Eclipse Juno, as well as the latest Android SDK (currently 4.2.2).
2.  Make sure both Eclipse project directories are placed within the same directory, for example Eclipse's workspace/ directory.
3.  Import both Eclipse projects through File > New > Project > Android Project from Existing Code, and browse for the Eclipse project folders.
4.  Allow both projects to build.  If there are errors, select Project > Clean and clean both projects.
5.  Build and run by selecting Run > Run, and launch to either an attached Android device or a previously created Android Emulator using the AVD Manager included with the Android SDK.


### Unimplement Features/ TODO ###

1.  Chat has no support for choosing your audience.  Currently it just posts to your current faction's audience.
2.  Server side e-mail code does not yet exist, though the screen is in the Android application.
3.  Artwork should be replaced.  All icons and logos are temporary and should not be published.
4.  Better server side error messages and client side display would help user feedback.
5.  Application is not currently hosted on the Play Store.

PHP API
-------------

This is a simple API that interfaces with the current HvZ database.  It is a pure PHP application written for PHP 5.2.15, as this is the current version supported by the HvZ webserver.  All URLs are meant to be accessed with GET requests, and if a route is meant to return data, it returns it as a JSON object.  Each JSON object matches exactly the columns of the database table that the API call refers to.

### Installation ###
Simply copy all files into the httpsdocs/api/ folder on the current webserver.
The file db.php contains database configuration information.  This will need to be filled in for future games.

### API Base URL ###
http://hvz.gatech.edu/api

### Current Routes ###
* **/player/{gt_name}**

    Fetches the player with the GT login {gt_name}
    
* **/kill?playerCode={code}&feed1={gt_name}&feed2={gt_name}&lat={lat}&lng={lng}**

    Tries to register a kill.  Needs a special {code} from a human, two Zombie {gt_name}'s, and integer {lat},{lng} as the current website uses.

* **/mission/{status}**

    Fetches missions for the current player's faction where {status} is one of [ACTIVE, ISSUED, CLOSED]
    
* **/faction/{faction}**

    Fetches a list of of players that belong to {faction} where faction is one of [HUMAN, ZOMBIE, ADMIN, ALL]
    
* **/achievements/{type}**

    Fetches a list of achievements of where {type} is one of [locked, unlocked]
    
* **/chat/{id}**

    Fetches all the chat messages starting after the chat message with id of {id}.  {id} = -1 for all messages.

* **/chat/post?message={message}**

    Post a new message to your currnet faction to the chat database.  Posts the contents of URL encoded content of {message}.
