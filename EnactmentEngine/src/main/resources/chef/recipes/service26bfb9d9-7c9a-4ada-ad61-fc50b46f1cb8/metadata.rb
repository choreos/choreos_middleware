maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

