maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/f297d74d-e01c-414c-88c1-a0b9bb07f54b/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/f297d74d-e01c-414c-88c1-a0b9bb07f54b/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/f297d74d-e01c-414c-88c1-a0b9bb07f54b/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

