maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/8c111f57-d02d-4f98-97c1-1b16003355d6/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/8c111f57-d02d-4f98-97c1-1b16003355d6/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/8c111f57-d02d-4f98-97c1-1b16003355d6/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

