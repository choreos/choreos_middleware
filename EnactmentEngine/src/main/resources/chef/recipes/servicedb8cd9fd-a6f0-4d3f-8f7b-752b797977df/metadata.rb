maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/db8cd9fd-a6f0-4d3f-8f7b-752b797977df/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/db8cd9fd-a6f0-4d3f-8f7b-752b797977df/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/db8cd9fd-a6f0-4d3f-8f7b-752b797977df/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

