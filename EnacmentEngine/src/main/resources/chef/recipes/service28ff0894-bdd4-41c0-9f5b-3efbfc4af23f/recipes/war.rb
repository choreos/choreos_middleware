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

if not node['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/28ff0894-bdd4-41c0-9f5b-3efbfc4af23f.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/28ff0894-bdd4-41c0-9f5b-3efbfc4af23f.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['deactivate']
	ruby_block "remove-service-28ff0894-bdd4-41c0-9f5b-3efbfc4af23f" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/28ff0894-bdd4-41c0-9f5b-3efbfc4af23f.war]"
	end
end
