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

if node['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['NumberOfClients']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/62f300fc-a6c4-47f5-bc5c-899bb7dc9837.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if not node['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['NumberOfClients']
	file "#{node['tomcat']['webapp_dir']}/62f300fc-a6c4-47f5-bc5c-899bb7dc9837.war" do
		action :delete
	end
end
