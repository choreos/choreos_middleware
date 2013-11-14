maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/0d0bab76-c651-4605-a8da-741e8524a71b/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/0d0bab76-c651-4605-a8da-741e8524a71b/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/0d0bab76-c651-4605-a8da-741e8524a71b/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

