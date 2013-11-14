maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/da7186c4-a6b1-46d2-b2b6-7c7ed785c953/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/da7186c4-a6b1-46d2-b2b6-7c7ed785c953/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/da7186c4-a6b1-46d2-b2b6-7c7ed785c953/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

