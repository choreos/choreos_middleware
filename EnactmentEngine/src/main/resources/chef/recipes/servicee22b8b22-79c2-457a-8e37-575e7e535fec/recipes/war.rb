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

if not node['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/e22b8b22-79c2-457a-8e37-575e7e535fec.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/e22b8b22-79c2-457a-8e37-575e7e535fec.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['deactivate']
	ruby_block "remove-service-e22b8b22-79c2-457a-8e37-575e7e535fec" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e22b8b22-79c2-457a-8e37-575e7e535fec.war]"
	end
end
