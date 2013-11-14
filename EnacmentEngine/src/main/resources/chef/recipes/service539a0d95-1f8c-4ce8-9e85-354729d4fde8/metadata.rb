maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/539a0d95-1f8c-4ce8-9e85-354729d4fde8/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/539a0d95-1f8c-4ce8-9e85-354729d4fde8/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/539a0d95-1f8c-4ce8-9e85-354729d4fde8/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

