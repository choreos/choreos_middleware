maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/2122d5d0-c730-4c32-a2e8-89316b138d1d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/2122d5d0-c730-4c32-a2e8-89316b138d1d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/2122d5d0-c730-4c32-a2e8-89316b138d1d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

