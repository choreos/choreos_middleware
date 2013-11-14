maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/ec807701-6356-4c68-bd3c-244f1046fe5d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/ec807701-6356-4c68-bd3c-244f1046fe5d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/ec807701-6356-4c68-bd3c-244f1046fe5d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

