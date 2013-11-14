maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/e9922235-fef4-4ec7-a097-788171153226/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/e9922235-fef4-4ec7-a097-788171153226/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/e9922235-fef4-4ec7-a097-788171153226/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

