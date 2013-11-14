maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/30d6cdd5-9372-4c45-93f6-3ed13d381a1e/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/30d6cdd5-9372-4c45-93f6-3ed13d381a1e/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/30d6cdd5-9372-4c45-93f6-3ed13d381a1e/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

