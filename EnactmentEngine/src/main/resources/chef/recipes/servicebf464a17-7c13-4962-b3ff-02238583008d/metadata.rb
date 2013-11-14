maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/bf464a17-7c13-4962-b3ff-02238583008d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/bf464a17-7c13-4962-b3ff-02238583008d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/bf464a17-7c13-4962-b3ff-02238583008d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

