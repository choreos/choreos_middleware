maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/b9f281ce-7706-4fb7-8641-8bef42e35f8c/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/b9f281ce-7706-4fb7-8641-8bef42e35f8c/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/b9f281ce-7706-4fb7-8641-8bef42e35f8c/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

