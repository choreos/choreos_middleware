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

if not node['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/2b792bce-170a-4c64-a606-94bf92e87a6a.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/2b792bce-170a-4c64-a606-94bf92e87a6a.war" do
		action :delete
	end
end
