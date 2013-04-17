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

* **/mission/{status}**

    Fetches missions for the current player's faction where {status} is one of [ACTIVE, ISSUED, CLOSED]
    
* **/faction/{faction}**

    Fetches a list of of players that belong to {faction} where faction is one of [HUMAN, ZOMBIE, ADMIN, ALL]
    
* **/achievements**

    Fetches a list of all achievements earned by the logged in player
    
* **/chat/{id}**

    Fetches all the chat messages starting after the chat message with id of {id}
    
* **/messages/new**

    Fetches only new messages for currently logged in player.

* **/messages?count={count}&offset={offset}**

    Fetches only old messages for currently logged in player.  {count} messages are fetched starting at {offset}, 0 being the most recently retrieved message.
    {count} defaults to 10, {offset} defaults to 0

* **/messages/sent**

    Fetches all sent messages from the currently logged in player.

* **/messages/compose?to={gt_name}&message={message_content}**

    Creates a new message to the given {gt_name} with the provided {message_content}
