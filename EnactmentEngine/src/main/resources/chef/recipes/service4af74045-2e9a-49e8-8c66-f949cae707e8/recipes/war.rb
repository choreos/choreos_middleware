#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################

include_recipe "apt" # java recipe is failing without recipe apt (and tomcat depends on java)
include_recipe "tomcat::choreos"

if not node['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/4af74045-2e9a-49e8-8c66-f949cae707e8.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/4af74045-2e9a-49e8-8c66-f949cae707e8.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['deactivate']
	ruby_block "remove-service-4af74045-2e9a-49e8-8c66-f949cae707e8" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4af74045-2e9a-49e8-8c66-f949cae707e8.war]"
	end
end
