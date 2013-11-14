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

if not node['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/b5b6be6f-ca9b-4f57-a14f-c6af5035061e.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/b5b6be6f-ca9b-4f57-a14f-c6af5035061e.war" do
		action :delete
	end
end
