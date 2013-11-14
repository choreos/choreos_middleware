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

if not node['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/fd87d081-a547-4c40-b0a8-ac9db04266ff.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/fd87d081-a547-4c40-b0a8-ac9db04266ff.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['deactivate']
	ruby_block "remove-service-fd87d081-a547-4c40-b0a8-ac9db04266ff" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/fd87d081-a547-4c40-b0a8-ac9db04266ff.war]"
	end
end
