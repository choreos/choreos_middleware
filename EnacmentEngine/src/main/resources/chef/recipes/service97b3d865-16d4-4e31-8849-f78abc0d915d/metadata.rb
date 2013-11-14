maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/97b3d865-16d4-4e31-8849-f78abc0d915d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/97b3d865-16d4-4e31-8849-f78abc0d915d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/97b3d865-16d4-4e31-8849-f78abc0d915d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

