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

if not node['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/aa1deff1-9a1b-49c6-854f-0f2bc7328a93.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/aa1deff1-9a1b-49c6-854f-0f2bc7328a93.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['deactivate']
	ruby_block "remove-service-aa1deff1-9a1b-49c6-854f-0f2bc7328a93" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/aa1deff1-9a1b-49c6-854f-0f2bc7328a93.war]"
	end
end
