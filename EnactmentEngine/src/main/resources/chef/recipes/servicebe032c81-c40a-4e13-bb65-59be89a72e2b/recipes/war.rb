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

if not node['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/be032c81-c40a-4e13-bb65-59be89a72e2b.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/be032c81-c40a-4e13-bb65-59be89a72e2b.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['deactivate']
	ruby_block "remove-service-be032c81-c40a-4e13-bb65-59be89a72e2b" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/be032c81-c40a-4e13-bb65-59be89a72e2b.war]"
	end
end
