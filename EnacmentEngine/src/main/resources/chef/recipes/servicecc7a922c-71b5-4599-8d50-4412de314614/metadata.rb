maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/cc7a922c-71b5-4599-8d50-4412de314614/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/cc7a922c-71b5-4599-8d50-4412de314614/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/cc7a922c-71b5-4599-8d50-4412de314614/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

