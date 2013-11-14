maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/dd9d69b8-234f-4a78-ac3a-1bbb81e4a134/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/dd9d69b8-234f-4a78-ac3a-1bbb81e4a134/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/dd9d69b8-234f-4a78-ac3a-1bbb81e4a134/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

