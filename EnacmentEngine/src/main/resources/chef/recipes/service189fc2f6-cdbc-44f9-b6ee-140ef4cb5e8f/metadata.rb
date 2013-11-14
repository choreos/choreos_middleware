maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

