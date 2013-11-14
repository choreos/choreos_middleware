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

if not node['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/68e6afa0-382e-412a-aa7d-038ffc740d2f.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/68e6afa0-382e-412a-aa7d-038ffc740d2f.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['deactivate']
	ruby_block "remove-service-68e6afa0-382e-412a-aa7d-038ffc740d2f" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/68e6afa0-382e-412a-aa7d-038ffc740d2f.war]"
	end
end
