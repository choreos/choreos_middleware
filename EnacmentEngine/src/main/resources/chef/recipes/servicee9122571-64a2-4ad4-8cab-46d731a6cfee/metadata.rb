maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/e9122571-64a2-4ad4-8cab-46d731a6cfee/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/e9122571-64a2-4ad4-8cab-46d731a6cfee/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/e9122571-64a2-4ad4-8cab-46d731a6cfee/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

