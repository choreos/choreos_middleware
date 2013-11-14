maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/fe000264-a817-413a-8dc3-6ce78ecd7ccf/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/fe000264-a817-413a-8dc3-6ce78ecd7ccf/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/fe000264-a817-413a-8dc3-6ce78ecd7ccf/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

