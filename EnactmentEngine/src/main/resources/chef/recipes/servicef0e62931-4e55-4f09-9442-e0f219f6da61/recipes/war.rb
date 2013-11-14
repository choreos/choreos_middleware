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

if not node['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/f0e62931-4e55-4f09-9442-e0f219f6da61.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/f0e62931-4e55-4f09-9442-e0f219f6da61.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['deactivate']
	ruby_block "remove-service-f0e62931-4e55-4f09-9442-e0f219f6da61" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f0e62931-4e55-4f09-9442-e0f219f6da61.war]"
	end
end
