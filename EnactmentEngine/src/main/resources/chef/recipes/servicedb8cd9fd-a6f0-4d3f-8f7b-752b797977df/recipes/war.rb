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

if not node['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/db8cd9fd-a6f0-4d3f-8f7b-752b797977df.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/db8cd9fd-a6f0-4d3f-8f7b-752b797977df.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['deactivate']
	ruby_block "remove-service-db8cd9fd-a6f0-4d3f-8f7b-752b797977df" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/db8cd9fd-a6f0-4d3f-8f7b-752b797977df.war]"
	end
end
