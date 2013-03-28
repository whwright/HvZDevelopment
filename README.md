HvZDevelopment
==============
- - -

Introduction
------------
An Android application to support the Georgia Tech Humans vs. Zombies game.

More information can be found at [HvZ](https://hvz.gatech.edu).

Android Specifics
-------------
This project is currently being built with the following options:

* Min SDK: Android 2.3.3
* Target SDK: Android 4.2
* Build SDK: Android 4.2

API
-------------
We are developing a web API for the HvZ application.
Data sources are not currently described here, but all requests return json data to be
parsed by the client application.

### API Base URL ###
http://hvz.gatech.edu/api

### Current Routes ###
* **/player/{gt_name}**

    Fetches the player with the GT login {gt_name}
    
* **/player/{gt_name}/kills**

    Fetches the kills by player {gt_name}

* **/mission/{faction}**

    Fetches missions for {faction} where faction is one of [HUMAN, ZOMBIE, ADMIN, ALL]

* **/mission/{faction}/current**

    Fetches current missions for {faction} where faction is one of [HUMAN, ZOMBIE, ADMIN, ALL]
    
* **/faction/{faction}


    Fetches a list of of players that belond to {faction}
