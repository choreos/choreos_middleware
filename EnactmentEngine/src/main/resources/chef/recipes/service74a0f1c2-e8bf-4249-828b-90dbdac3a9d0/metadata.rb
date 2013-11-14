maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/74a0f1c2-e8bf-4249-828b-90dbdac3a9d0/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/74a0f1c2-e8bf-4249-828b-90dbdac3a9d0/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/74a0f1c2-e8bf-4249-828b-90dbdac3a9d0/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

