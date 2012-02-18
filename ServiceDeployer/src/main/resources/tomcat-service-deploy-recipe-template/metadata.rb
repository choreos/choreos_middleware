maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "tomcat"


attribute "service/$NAME/WarFile",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"

attribute "service/$NAME/webappsPath",
  :display_NAME => "Path for the webapps folder to deploy in Tomcat",
  :default => "/var/lib/webapps"

attribute "service/$NAME/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

