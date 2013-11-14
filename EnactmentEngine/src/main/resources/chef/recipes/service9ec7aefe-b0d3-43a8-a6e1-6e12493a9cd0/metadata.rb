maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

