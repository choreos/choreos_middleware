maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/c909df1b-ea4a-41d1-a255-c252eda3f80d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/c909df1b-ea4a-41d1-a255-c252eda3f80d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/c909df1b-ea4a-41d1-a255-c252eda3f80d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

