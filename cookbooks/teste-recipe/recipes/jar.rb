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


service "service_teste_jar" do
	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-teste.pid --exec /bin/bash -- -c \"echo $$ > /var/run/service-teste.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['teste']['InstallationDir']}/service-teste.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-teste.pid"
 	action :nothing
 	supports :start => true, :stop => true
end

if node['CHOReOSData']['serviceData']['teste']['NumberOfClients'] > 0
	remote_file "#{node['CHOReOSData']['serviceData']['teste']['InstallationDir']}/service-teste.jar" do
  		source "#{node['CHOReOSData']['serviceData']['teste']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_teste_jar]"
		notifies :start, "service[service_teste_jar]"
	end
end

if node['CHOReOSData']['serviceData']['teste']['NumberOfClients'] <= 0
	file "#{node['CHOReOSData']['serviceData']['teste']['InstallationDir']}/service-teste.jar" do
		notifies :stop, "service[service_teste_jar]", :immediately
		#notifies :disable, "service[service_teste_jar]", :immediately
		action :delete
	end
end
