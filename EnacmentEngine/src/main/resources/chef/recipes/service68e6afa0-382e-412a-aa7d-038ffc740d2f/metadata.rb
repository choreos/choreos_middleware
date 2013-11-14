maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/68e6afa0-382e-412a-aa7d-038ffc740d2f/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/68e6afa0-382e-412a-aa7d-038ffc740d2f/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/68e6afa0-382e-412a-aa7d-038ffc740d2f/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

