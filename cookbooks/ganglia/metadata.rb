maintainer       "Heavy Water Software Inc."
maintainer_email "darrin@heavywater.ca"
license          "Apache 2.0"
description      "Installs/Configures ganglia - modified to work in the CHOReOS context by <lago@ime.usp.br> - modified to work with chef-solo by <tfmend@gmail.com.br>"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.rdoc'))
version          "0.1.4"

%w{ debian ubuntu}.each do |os|
  supports os
end

suggests "graphite"
suggests "iptables"
