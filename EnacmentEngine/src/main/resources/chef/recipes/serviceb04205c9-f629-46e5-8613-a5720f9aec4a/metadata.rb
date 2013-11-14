maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/b04205c9-f629-46e5-8613-a5720f9aec4a/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/b04205c9-f629-46e5-8613-a5720f9aec4a/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/b04205c9-f629-46e5-8613-a5720f9aec4a/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

