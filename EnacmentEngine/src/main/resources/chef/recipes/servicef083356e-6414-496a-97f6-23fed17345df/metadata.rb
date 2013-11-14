maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/f083356e-6414-496a-97f6-23fed17345df/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/f083356e-6414-496a-97f6-23fed17345df/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/f083356e-6414-496a-97f6-23fed17345df/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

