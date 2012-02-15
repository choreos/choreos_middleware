maintainer       "CHOReOS"
maintainer_email "nelson.lago@gmail.com"
license          "Apache 2.0"
description      "Creates a database identifiable by a UUID in a database"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.rdoc'))
version          "1.0.0"

depends "mysql"

attribute "storage/$UUID/id",
  :display_name => "the uuid of this schema",
  :default => "test"

attribute "storage/$UUID/schema",
  :display_name => "the name of this schema3",
  :default => "test"

attribute "storage/$UUID/dbuser",
  :display_name => "the user authorized to access this schema",
  :default => "test"

attribute "storage/$UUID/dbpassword",
  :display_name => "the password",
  :default => "test"

attribute "storage/$UUID/type",
  :display_name => "the storageuuid type",
  :default => "test"

attribute "storage/$UUID/sqlscript",
  :display_name => "the script to create the schema",
  :default => "test"
