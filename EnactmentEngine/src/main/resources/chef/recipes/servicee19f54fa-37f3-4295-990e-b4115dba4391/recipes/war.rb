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

if not node['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/e19f54fa-37f3-4295-990e-b4115dba4391.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/e19f54fa-37f3-4295-990e-b4115dba4391.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['deactivate']
	ruby_block "remove-service-e19f54fa-37f3-4295-990e-b4115dba4391" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e19f54fa-37f3-4295-990e-b4115dba4391.war]"
	end
end
