maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/78681bda-3abe-4aa5-b10e-64dd3aed4f03/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/78681bda-3abe-4aa5-b10e-64dd3aed4f03/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/78681bda-3abe-4aa5-b10e-64dd3aed4f03/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

