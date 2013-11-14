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

if node['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['NumberOfClients']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/e825e501-5f10-4a19-966e-4f6d669950ea.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if not node['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['NumberOfClients']
	file "#{node['tomcat']['webapp_dir']}/e825e501-5f10-4a19-966e-4f6d669950ea.war" do
		action :delete
	end
end
