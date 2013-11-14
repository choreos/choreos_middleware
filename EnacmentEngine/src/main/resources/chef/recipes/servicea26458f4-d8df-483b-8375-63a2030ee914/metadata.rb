maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/a26458f4-d8df-483b-8375-63a2030ee914/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/a26458f4-d8df-483b-8375-63a2030ee914/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/a26458f4-d8df-483b-8375-63a2030ee914/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

