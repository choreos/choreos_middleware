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

remote_file "war_file" do
	source "#{node['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['PackageURL']}"
	path "#{node['tomcat']['webapp_dir']}/30049b2a-a7cf-4a73-a7bb-c66e58a66cee.war"
	mode "0755"
	action :nothing
end

if not node['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['deactivate']
	ruby_block "install-file-30049b2a-a7cf-4a73-a7bb-c66e58a66cee" do
		block do
			# do nothing!
			i = 0
		end
		action: create_if_missing, "remote_file[war_file]" 
	end
end

file "#{node['tomcat']['webapp_dir']}/30049b2a-a7cf-4a73-a7bb-c66e58a66cee.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['deactivate']
	ruby_block "uninstall-file-30049b2a-a7cf-4a73-a7bb-c66e58a66cee" do
		block do
			# do nothing!
			i = 0
		end
		action: delete, file[#{node['tomcat']['webapp_dir']}/30049b2a-a7cf-4a73-a7bb-c66e58a66cee.war]
	end
end
