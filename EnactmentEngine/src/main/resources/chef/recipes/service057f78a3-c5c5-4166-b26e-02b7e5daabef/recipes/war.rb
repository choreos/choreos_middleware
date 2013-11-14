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

if not node['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/057f78a3-c5c5-4166-b26e-02b7e5daabef.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/057f78a3-c5c5-4166-b26e-02b7e5daabef.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['deactivate']
	ruby_block "remove-service-057f78a3-c5c5-4166-b26e-02b7e5daabef" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/057f78a3-c5c5-4166-b26e-02b7e5daabef.war]"
	end
end
