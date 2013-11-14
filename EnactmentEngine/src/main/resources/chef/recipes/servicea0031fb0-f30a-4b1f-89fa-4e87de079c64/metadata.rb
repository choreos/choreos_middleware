maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/a0031fb0-f30a-4b1f-89fa-4e87de079c64/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/a0031fb0-f30a-4b1f-89fa-4e87de079c64/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/a0031fb0-f30a-4b1f-89fa-4e87de079c64/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

