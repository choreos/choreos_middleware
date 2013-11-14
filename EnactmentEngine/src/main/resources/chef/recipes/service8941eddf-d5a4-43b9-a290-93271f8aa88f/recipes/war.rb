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

if not node['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/8941eddf-d5a4-43b9-a290-93271f8aa88f.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/8941eddf-d5a4-43b9-a290-93271f8aa88f.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['deactivate']
	ruby_block "remove-service-8941eddf-d5a4-43b9-a290-93271f8aa88f" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/8941eddf-d5a4-43b9-a290-93271f8aa88f.war]"
	end
end
