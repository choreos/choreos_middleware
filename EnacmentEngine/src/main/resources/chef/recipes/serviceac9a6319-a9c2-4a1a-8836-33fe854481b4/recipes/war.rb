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
	source "#{node['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['PackageURL']}"
	path "#{node['tomcat']['webapp_dir']}/ac9a6319-a9c2-4a1a-8836-33fe854481b4.war"
	mode "0755"
	action :nothing
end

if not node['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['deactivate']
	ruby_block "install-file-ac9a6319-a9c2-4a1a-8836-33fe854481b4" do
		block do
			# do nothing!
			i = 0
		end
		action: create_if_missing, "remote_file[war_file]" 
	end
end

file "#{node['tomcat']['webapp_dir']}/ac9a6319-a9c2-4a1a-8836-33fe854481b4.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['deactivate']
	ruby_block "uninstall-file-ac9a6319-a9c2-4a1a-8836-33fe854481b4" do
		block do
			# do nothing!
			i = 0
		end
		action: delete, file[#{node['tomcat']['webapp_dir']}/ac9a6319-a9c2-4a1a-8836-33fe854481b4.war]
	end
end
