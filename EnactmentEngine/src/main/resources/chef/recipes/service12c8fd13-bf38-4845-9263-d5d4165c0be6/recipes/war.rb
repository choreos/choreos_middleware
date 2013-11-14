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

if not node['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/12c8fd13-bf38-4845-9263-d5d4165c0be6.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/12c8fd13-bf38-4845-9263-d5d4165c0be6.war" do
		action :delete
	end
end
