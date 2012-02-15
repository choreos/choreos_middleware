maintainer       "CHOReOS"
maintainer_email "nelson.lago@gmail.com"
license          "Apache 2.0"
description      "Creates a database identifiable by a UUID in a database"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.rdoc'))
version          "0.1.0"


recipe            "storage-$UUID", "creates a database, based on the attributes set for the $UUID correlation ID"
