maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/f2b7636b-4b8e-4653-80b6-c284f5db3cba/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/f2b7636b-4b8e-4653-80b6-c284f5db3cba/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/f2b7636b-4b8e-4653-80b6-c284f5db3cba/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

