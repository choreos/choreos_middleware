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

if not node['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/934ce62f-7b42-41af-88b3-a6409c19c5f6.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/934ce62f-7b42-41af-88b3-a6409c19c5f6.war" do
		action :delete
	end
end
