maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/ac9a6319-a9c2-4a1a-8836-33fe854481b4/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/ac9a6319-a9c2-4a1a-8836-33fe854481b4/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/ac9a6319-a9c2-4a1a-8836-33fe854481b4/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

