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

if not node['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/e782bc01-3ceb-411a-b79e-73a687ddaa49.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/e782bc01-3ceb-411a-b79e-73a687ddaa49.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['deactivate']
	ruby_block "remove-service-e782bc01-3ceb-411a-b79e-73a687ddaa49" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e782bc01-3ceb-411a-b79e-73a687ddaa49.war]"
	end
end
