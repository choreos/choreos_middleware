maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/e825e501-5f10-4a19-966e-4f6d669950ea/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/e825e501-5f10-4a19-966e-4f6d669950ea/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/e825e501-5f10-4a19-966e-4f6d669950ea/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

