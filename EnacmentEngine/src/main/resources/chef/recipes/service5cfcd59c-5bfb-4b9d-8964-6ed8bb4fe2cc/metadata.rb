maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

