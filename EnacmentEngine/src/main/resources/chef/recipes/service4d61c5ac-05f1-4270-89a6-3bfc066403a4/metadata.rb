maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/4d61c5ac-05f1-4270-89a6-3bfc066403a4/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/4d61c5ac-05f1-4270-89a6-3bfc066403a4/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/4d61c5ac-05f1-4270-89a6-3bfc066403a4/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

