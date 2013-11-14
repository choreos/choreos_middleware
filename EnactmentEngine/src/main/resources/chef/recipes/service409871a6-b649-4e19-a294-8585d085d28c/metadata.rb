maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/409871a6-b649-4e19-a294-8585d085d28c/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/409871a6-b649-4e19-a294-8585d085d28c/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/409871a6-b649-4e19-a294-8585d085d28c/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

