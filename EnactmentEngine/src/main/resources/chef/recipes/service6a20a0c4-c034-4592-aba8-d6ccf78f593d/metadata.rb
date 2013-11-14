maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/6a20a0c4-c034-4592-aba8-d6ccf78f593d/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/6a20a0c4-c034-4592-aba8-d6ccf78f593d/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/6a20a0c4-c034-4592-aba8-d6ccf78f593d/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

