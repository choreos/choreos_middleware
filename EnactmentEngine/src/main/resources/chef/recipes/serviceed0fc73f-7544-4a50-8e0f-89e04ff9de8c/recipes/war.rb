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

if not node['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['deactivate']
	ruby_block "remove-service-ed0fc73f-7544-4a50-8e0f-89e04ff9de8c" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c.war]"
	end
end
