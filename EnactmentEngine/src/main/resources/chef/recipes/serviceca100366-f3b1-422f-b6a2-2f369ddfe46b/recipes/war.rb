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

if not node['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/ca100366-f3b1-422f-b6a2-2f369ddfe46b.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/ca100366-f3b1-422f-b6a2-2f369ddfe46b.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['deactivate']
	ruby_block "remove-service-ca100366-f3b1-422f-b6a2-2f369ddfe46b" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ca100366-f3b1-422f-b6a2-2f369ddfe46b.war]"
	end
end
