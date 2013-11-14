maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/a64bf426-c2d0-4863-b30e-1f005df793a6/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/a64bf426-c2d0-4863-b30e-1f005df793a6/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/a64bf426-c2d0-4863-b30e-1f005df793a6/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

