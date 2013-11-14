maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/ca0c7031-9cc4-41e6-b95e-22277be565ff/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/ca0c7031-9cc4-41e6-b95e-22277be565ff/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/ca0c7031-9cc4-41e6-b95e-22277be565ff/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

