maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/be9a3c03-b71a-4456-af9d-9d2d2232c4de/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/be9a3c03-b71a-4456-af9d-9d2d2232c4de/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/be9a3c03-b71a-4456-af9d-9d2d2232c4de/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"
