maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/b1d2c984-ad24-4df4-b5ab-6514ea817a12/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/b1d2c984-ad24-4df4-b5ab-6514ea817a12/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/b1d2c984-ad24-4df4-b5ab-6514ea817a12/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

