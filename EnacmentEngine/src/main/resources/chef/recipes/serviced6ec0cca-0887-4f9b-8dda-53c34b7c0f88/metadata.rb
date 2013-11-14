maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/d6ec0cca-0887-4f9b-8dda-53c34b7c0f88/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/d6ec0cca-0887-4f9b-8dda-53c34b7c0f88/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/d6ec0cca-0887-4f9b-8dda-53c34b7c0f88/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

