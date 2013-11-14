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

if not node['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/df338b9d-86e6-4ee1-8618-4e66bcbf102f.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/df338b9d-86e6-4ee1-8618-4e66bcbf102f.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['deactivate']
	ruby_block "remove-service-df338b9d-86e6-4ee1-8618-4e66bcbf102f" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/df338b9d-86e6-4ee1-8618-4e66bcbf102f.war]"
	end
end
