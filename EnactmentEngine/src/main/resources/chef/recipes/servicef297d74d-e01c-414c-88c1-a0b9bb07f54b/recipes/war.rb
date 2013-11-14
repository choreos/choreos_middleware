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

if not node['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/f297d74d-e01c-414c-88c1-a0b9bb07f54b.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/f297d74d-e01c-414c-88c1-a0b9bb07f54b.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['deactivate']
	ruby_block "remove-service-f297d74d-e01c-414c-88c1-a0b9bb07f54b" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f297d74d-e01c-414c-88c1-a0b9bb07f54b.war]"
	end
end
