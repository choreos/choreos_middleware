maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/c1997e4e-1c06-4fe3-9617-b7f514383f04/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/c1997e4e-1c06-4fe3-9617-b7f514383f04/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/c1997e4e-1c06-4fe3-9617-b7f514383f04/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

