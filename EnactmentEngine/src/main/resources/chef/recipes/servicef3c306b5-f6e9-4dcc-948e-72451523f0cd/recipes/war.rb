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

if not node['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/f3c306b5-f6e9-4dcc-948e-72451523f0cd.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/f3c306b5-f6e9-4dcc-948e-72451523f0cd.war" do
		action :delete
	end
end
