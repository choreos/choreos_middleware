maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/eb1bd49f-93a9-4779-89d6-a8d7cab73aa7/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/eb1bd49f-93a9-4779-89d6-a8d7cab73aa7/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/eb1bd49f-93a9-4779-89d6-a8d7cab73aa7/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

