#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

include_recipe "apt" # java recipe is failing without recipe apt
include_recipe "java"


service "service_d9a6689c-78ea-4044-b150-2f281990e411_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-d9a6689c-78ea-4044-b150-2f281990e411.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-d9a6689c-78ea-4044-b150-2f281990e411.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['InstallationDir']}/service-d9a6689c-78ea-4044-b150-2f281990e411.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-d9a6689c-78ea-4044-b150-2f281990e411.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-d9a6689c-78ea-4044-b150-2f281990e411.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['InstallationDir']}/service-d9a6689c-78ea-4044-b150-2f281990e411.jar" do
  		source "#{node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_d9a6689c-78ea-4044-b150-2f281990e411_jar]"
		notifies :start, "service[service_d9a6689c-78ea-4044-b150-2f281990e411_jar]"
	end
end

if node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['InstallationDir']}/service-d9a6689c-78ea-4044-b150-2f281990e411.jar" do
		notifies :stop, "service[service_d9a6689c-78ea-4044-b150-2f281990e411_jar]", :immediately
		#notifies :disable, "service[service_d9a6689c-78ea-4044-b150-2f281990e411_jar]", :immediately
		action :delete
	end
end




# As the last resource in the dnsserver::remove_slave recipe, assuming that the remove_slave
# "undoes" a dnsserver slave installation of some kind, without knowing what that might have been.
#ruby_block "remove_this_recipe" do
#  block do
#    node.run_list.remove("recipe[dnsserver::remove_slave]") if node.run_list.include?("recipe[dnsserver::remove_slave]")
#  end
#  action :nothing
#end
