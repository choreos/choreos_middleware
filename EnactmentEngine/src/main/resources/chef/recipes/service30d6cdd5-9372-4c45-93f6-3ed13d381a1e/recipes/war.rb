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

if not node['CHOReOSData']['serviceData']['30d6cdd5-9372-4c45-93f6-3ed13d381a1e']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['30d6cdd5-9372-4c45-93f6-3ed13d381a1e']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/30d6cdd5-9372-4c45-93f6-3ed13d381a1e.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/30d6cdd5-9372-4c45-93f6-3ed13d381a1e.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['30d6cdd5-9372-4c45-93f6-3ed13d381a1e']['deactivate']
	ruby_block "remove-service-30d6cdd5-9372-4c45-93f6-3ed13d381a1e" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/30d6cdd5-9372-4c45-93f6-3ed13d381a1e.war]"
	end
end
