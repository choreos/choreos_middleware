maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/d78d77d5-5c95-40bb-9ef7-c688459e2bec/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/d78d77d5-5c95-40bb-9ef7-c688459e2bec/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/d78d77d5-5c95-40bb-9ef7-c688459e2bec/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

