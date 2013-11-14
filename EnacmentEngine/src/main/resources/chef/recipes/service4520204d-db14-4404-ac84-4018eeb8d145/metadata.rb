maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/4520204d-db14-4404-ac84-4018eeb8d145/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/4520204d-db14-4404-ac84-4018eeb8d145/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/4520204d-db14-4404-ac84-4018eeb8d145/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

