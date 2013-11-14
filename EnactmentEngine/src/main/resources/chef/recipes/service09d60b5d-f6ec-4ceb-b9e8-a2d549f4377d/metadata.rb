maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

