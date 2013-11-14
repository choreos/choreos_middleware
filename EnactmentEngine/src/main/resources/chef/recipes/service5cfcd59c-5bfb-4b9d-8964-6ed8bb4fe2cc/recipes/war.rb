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

if not node['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['deactivate']
	ruby_block "remove-service-5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc.war]"
	end
end
