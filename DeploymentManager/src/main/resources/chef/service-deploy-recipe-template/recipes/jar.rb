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


service "service_$NAME_jar" do
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-$NAME.pid --exec /bin/bash -- -c \"echo $$ > /var/run/service-$NAME.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-$NAME.pid"
 	action :nothing
 	supports :start => true, :stop => true
end

if node['CHOReOSData']['serviceData']['$NAME']['NumberOfClients'] > 0
	remote_file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
  		source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_$NAME_jar]"
		notifies :start, "service[service_$NAME_jar]"
	end
end

if node['CHOReOSData']['serviceData']['$NAME']['NumberOfClients'] <= 0
	file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
		notifies :stop, "service[service_$NAME_jar]", :immediately
		#notifies :disable, "service[service_$NAME_jar]", :immediately
		action :delete
	end
end