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

if not node['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/7e798134-184f-4d6a-8bab-e3866fa0881b.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/7e798134-184f-4d6a-8bab-e3866fa0881b.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['deactivate']
	ruby_block "remove-service-7e798134-184f-4d6a-8bab-e3866fa0881b" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7e798134-184f-4d6a-8bab-e3866fa0881b.war]"
	end
end
