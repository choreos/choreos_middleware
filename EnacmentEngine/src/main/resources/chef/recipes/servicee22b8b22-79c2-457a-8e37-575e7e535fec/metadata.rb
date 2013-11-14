maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/e22b8b22-79c2-457a-8e37-575e7e535fec/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/e22b8b22-79c2-457a-8e37-575e7e535fec/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/e22b8b22-79c2-457a-8e37-575e7e535fec/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

