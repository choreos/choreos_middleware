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

if not node['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/df6f7b76-c0d2-45e3-b216-8b599b27436d.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/df6f7b76-c0d2-45e3-b216-8b599b27436d.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['deactivate']
	ruby_block "remove-service-df6f7b76-c0d2-45e3-b216-8b599b27436d" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/df6f7b76-c0d2-45e3-b216-8b599b27436d.war]"
	end
end
