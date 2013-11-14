maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/1a144a5c-369d-4b52-898f-1a0b55b1e70e/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/1a144a5c-369d-4b52-898f-1a0b55b1e70e/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/1a144a5c-369d-4b52-898f-1a0b55b1e70e/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

