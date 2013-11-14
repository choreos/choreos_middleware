maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

