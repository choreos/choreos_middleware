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

if node['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['NumberOfClients']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/7dbbc6a9-4492-4633-80c8-ec4ef81f50dd.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if not node['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['NumberOfClients']
	file "#{node['tomcat']['webapp_dir']}/7dbbc6a9-4492-4633-80c8-ec4ef81f50dd.war" do
		action :delete
	end
end
