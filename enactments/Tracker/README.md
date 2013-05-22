Tracker Choreography Enactment
==============================

Build the war
-------------

    $ mvn -pl webservice package

The file webservice/target/webservice.war will be created

Build enacter.jar
-----------------
If you want to package the enacter for some reason (e.g. to run it on another
computer), install the webservice first:

    $ mvn -pl webservice install
    $ mvn package
