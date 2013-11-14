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

if not node['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/59271e33-b1b4-4b10-bf00-ee589f90d92d.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/59271e33-b1b4-4b10-bf00-ee589f90d92d.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['deactivate']
	ruby_block "remove-service-59271e33-b1b4-4b10-bf00-ee589f90d92d" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/59271e33-b1b4-4b10-bf00-ee589f90d92d.war]"
	end
end
