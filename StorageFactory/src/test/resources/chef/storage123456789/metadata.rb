maintainer       "CHOReOS"
maintainer_email "nelson.lago@gmail.com"
license          "Apache 2.0"
description      "Creates a database identifiable by a UUID in a database"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.rdoc'))
version          "0.1.1"

depends "mysql"

attribute "storage/12345789/uuid",
  :display_name => "the uuid of this schema",
  :default => "test"

attribute "storage/12345789/schema",
  :display_name => "the name of this schema3",
  :default => "test"

attribute "storage/12345789/dbuser",
  :display_name => "the user authorized to access this schema",
  :default => "test"

attribute "storage/12345789/dbpassword",
  :display_name => "the password",
  :default => "test"

attribute "storage/12345789/dbtype",
  :display_name => "the storageuuid type",
  :default => "test"

attribute "storage/12345789/sqlscript",
  :display_name => "the script to create the schema",
  :default => "test"
