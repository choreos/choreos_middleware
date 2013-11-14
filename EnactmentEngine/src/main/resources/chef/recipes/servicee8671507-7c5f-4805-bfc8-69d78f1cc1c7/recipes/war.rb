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

if not node['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/e8671507-7c5f-4805-bfc8-69d78f1cc1c7.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/e8671507-7c5f-4805-bfc8-69d78f1cc1c7.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['deactivate']
	ruby_block "remove-service-e8671507-7c5f-4805-bfc8-69d78f1cc1c7" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e8671507-7c5f-4805-bfc8-69d78f1cc1c7.war]"
	end
end
