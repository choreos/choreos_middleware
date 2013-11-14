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

if not node['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['deactivate']
	remote_file "war_file" do
  		source "#{node['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['PackageURL']}"
  		path "#{node['tomcat']['webapp_dir']}/2cb3a033-b2a4-4e42-8386-0e1ebc398c1a.war"
  		mode "0755"
  		action :create_if_missing
	end
end

file "#{node['tomcat']['webapp_dir']}/2cb3a033-b2a4-4e42-8386-0e1ebc398c1a.war" do
	action :nothing
end

if node['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['deactivate']
	ruby_block "remove-service-2cb3a033-b2a4-4e42-8386-0e1ebc398c1a" do
		block do
			i=0
		end
		notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2cb3a033-b2a4-4e42-8386-0e1ebc398c1a.war]"
	end
end
