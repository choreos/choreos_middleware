maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/c78b6583-dd1f-477f-a3d6-e301855f7d6f/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/c78b6583-dd1f-477f-a3d6-e301855f7d6f/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/c78b6583-dd1f-477f-a3d6-e301855f7d6f/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

