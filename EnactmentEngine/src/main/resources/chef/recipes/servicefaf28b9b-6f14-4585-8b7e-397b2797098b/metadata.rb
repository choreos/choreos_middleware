maintainer       "CHOReOS"
maintainer_email "felipepg@ime.usp.br"
license          "bla"
description      "Deploys a WAR file into a cloud node"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.0.1"

depends "apt"
depends "tomcat"

attribute "service/faf28b9b-6f14-4585-8b7e-397b2797098b/WarFile",
  :display_NAME => "WAR file name to be deployed",
  :default => "test"

attribute "service/faf28b9b-6f14-4585-8b7e-397b2797098b/URL",
  :display_NAME => "Location (URL) of the WAR file to be deployed",
  :default => "test"


attribute "service/faf28b9b-6f14-4585-8b7e-397b2797098b/logfile",
  :display_NAME => "The default log file for errors",
  :default => "/dev/null"

