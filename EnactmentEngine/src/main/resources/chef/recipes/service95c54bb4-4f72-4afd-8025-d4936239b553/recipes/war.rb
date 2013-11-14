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

if not node['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/95c54bb4-4f72-4afd-8025-d4936239b553.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/95c54bb4-4f72-4afd-8025-d4936239b553.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['deactivate']
	ruby_block "remove-service-95c54bb4-4f72-4afd-8025-d4936239b553" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/95c54bb4-4f72-4afd-8025-d4936239b553.war]"
	end
end
