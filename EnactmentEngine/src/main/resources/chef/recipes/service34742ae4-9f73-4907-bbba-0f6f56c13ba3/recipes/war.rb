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

if node['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['NumberOfClients']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/34742ae4-9f73-4907-bbba-0f6f56c13ba3.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if not node['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['NumberOfClients']
	file "#{node['tomcat']['webapp_dir']}/34742ae4-9f73-4907-bbba-0f6f56c13ba3.war" do
		action :delete
	end
end
