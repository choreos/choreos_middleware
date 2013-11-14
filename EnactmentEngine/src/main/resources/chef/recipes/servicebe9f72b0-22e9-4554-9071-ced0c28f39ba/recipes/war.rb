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

if not node['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/be9f72b0-22e9-4554-9071-ced0c28f39ba.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/be9f72b0-22e9-4554-9071-ced0c28f39ba.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['deactivate']
	ruby_block "remove-service-be9f72b0-22e9-4554-9071-ced0c28f39ba" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/be9f72b0-22e9-4554-9071-ced0c28f39ba.war]"
	end
end
