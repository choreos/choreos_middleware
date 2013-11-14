maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/af9b2971-994c-4184-95d0-7c284563e31c/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/af9b2971-994c-4184-95d0-7c284563e31c/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/af9b2971-994c-4184-95d0-7c284563e31c/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

