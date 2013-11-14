maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/80b28b07-aff7-4f61-8634-309547a4484b/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/80b28b07-aff7-4f61-8634-309547a4484b/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/80b28b07-aff7-4f61-8634-309547a4484b/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

