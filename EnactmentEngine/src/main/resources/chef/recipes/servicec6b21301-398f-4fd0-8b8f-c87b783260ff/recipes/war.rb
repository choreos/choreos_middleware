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

if not node['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/c6b21301-398f-4fd0-8b8f-c87b783260ff.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/c6b21301-398f-4fd0-8b8f-c87b783260ff.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['deactivate']
	ruby_block "remove-service-c6b21301-398f-4fd0-8b8f-c87b783260ff" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c6b21301-398f-4fd0-8b8f-c87b783260ff.war]"
	end
end
