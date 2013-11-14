maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/a7afd39d-3c84-4899-9f46-3fdee245ff11/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/a7afd39d-3c84-4899-9f46-3fdee245ff11/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/a7afd39d-3c84-4899-9f46-3fdee245ff11/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

