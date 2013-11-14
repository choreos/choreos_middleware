maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/d08bf37c-19c0-4038-b31a-61a3f13c72ae/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/d08bf37c-19c0-4038-b31a-61a3f13c72ae/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/d08bf37c-19c0-4038-b31a-61a3f13c72ae/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

