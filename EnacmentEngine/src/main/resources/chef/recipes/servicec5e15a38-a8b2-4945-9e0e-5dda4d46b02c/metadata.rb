maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/c5e15a38-a8b2-4945-9e0e-5dda4d46b02c/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/c5e15a38-a8b2-4945-9e0e-5dda4d46b02c/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/c5e15a38-a8b2-4945-9e0e-5dda4d46b02c/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

