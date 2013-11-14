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

remote_file "war_file" do
	source "#{node['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['PackageURL']}"
	path "#{node['tomcat']['webapp_dir']}/50672d2b-725c-4baf-ba99-973fee9a7943.war"
	mode "0755"
	action :nothing
end

if not node['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['deactivate']
	remote_file "war_file" do
		source "#{node['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['PackageURL']}"
		path "#{node['tomcat']['webapp_dir']}/50672d2b-725c-4baf-ba99-973fee9a7943.war"
		mode "0755"
		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/50672d2b-725c-4baf-ba99-973fee9a7943.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['deactivate']
	ruby_block "uninstall-file-50672d2b-725c-4baf-ba99-973fee9a7943" do
		block do
			# do nothing!
			i = 0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/50672d2b-725c-4baf-ba99-973fee9a7943.war]"
	end
end
