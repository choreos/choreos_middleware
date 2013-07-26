# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

include_recipe "apt" # java recipe is failing without recipe apt (and tomcat depends on java)
include_recipe "tomcat::choreos"

remote_file "war_file" do
	source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
	path "#{node['tomcat']['webapp_dir']}/$NAME.war"
	mode "0755"
	action :create_if_missing
end

file "#{node['tomcat']['webapp_dir']}/$NAME.war" do
	action :nothing
end
