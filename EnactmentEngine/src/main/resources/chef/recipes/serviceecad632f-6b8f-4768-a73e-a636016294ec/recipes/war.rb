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

if not node['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/ecad632f-6b8f-4768-a73e-a636016294ec.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/ecad632f-6b8f-4768-a73e-a636016294ec.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['deactivate']
	ruby_block "remove-service-ecad632f-6b8f-4768-a73e-a636016294ec" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ecad632f-6b8f-4768-a73e-a636016294ec.war]"
	end
end
