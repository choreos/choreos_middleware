maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/5e8a1237-31e2-47a4-9f80-8840a6c7724d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/5e8a1237-31e2-47a4-9f80-8840a6c7724d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/5e8a1237-31e2-47a4-9f80-8840a6c7724d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

