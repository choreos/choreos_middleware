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

if node['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['NumberOfClients']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/a7afd39d-3c84-4899-9f46-3fdee245ff11.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if not node['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['NumberOfClients']
	file "#{node['tomcat']['webapp_dir']}/a7afd39d-3c84-4899-9f46-3fdee245ff11.war" do
		action :delete
	end
end
