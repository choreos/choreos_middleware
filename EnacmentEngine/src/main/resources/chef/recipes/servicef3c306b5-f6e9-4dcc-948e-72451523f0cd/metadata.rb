maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/f3c306b5-f6e9-4dcc-948e-72451523f0cd/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/f3c306b5-f6e9-4dcc-948e-72451523f0cd/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/f3c306b5-f6e9-4dcc-948e-72451523f0cd/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

