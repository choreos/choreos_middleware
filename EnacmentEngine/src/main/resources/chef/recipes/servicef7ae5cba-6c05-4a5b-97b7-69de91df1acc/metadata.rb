maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/f7ae5cba-6c05-4a5b-97b7-69de91df1acc/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/f7ae5cba-6c05-4a5b-97b7-69de91df1acc/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/f7ae5cba-6c05-4a5b-97b7-69de91df1acc/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

