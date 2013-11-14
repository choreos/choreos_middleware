maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/e0a19a83-dae1-45bb-8db1-c4fd8ae562ba/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/e0a19a83-dae1-45bb-8db1-c4fd8ae562ba/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/e0a19a83-dae1-45bb-8db1-c4fd8ae562ba/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

