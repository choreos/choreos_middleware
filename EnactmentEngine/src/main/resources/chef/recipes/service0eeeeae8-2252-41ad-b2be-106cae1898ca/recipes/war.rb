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

if not node['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/0eeeeae8-2252-41ad-b2be-106cae1898ca.war"
  		mode "0755"
  		action :create_if_missing
	end
end

if node['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['deactivate']
	file "#{node['tomcat']['webapp_dir']}/0eeeeae8-2252-41ad-b2be-106cae1898ca.war" do
		action :delete
	end
end
