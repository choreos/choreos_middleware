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


service "service_8eadd4dc-90df-4631-844e-8248407c418d_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-8eadd4dc-90df-4631-844e-8248407c418d.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-8eadd4dc-90df-4631-844e-8248407c418d.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['InstallationDir']}/service-8eadd4dc-90df-4631-844e-8248407c418d.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-8eadd4dc-90df-4631-844e-8248407c418d.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-8eadd4dc-90df-4631-844e-8248407c418d.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['InstallationDir']}/service-8eadd4dc-90df-4631-844e-8248407c418d.jar" do
  		source "#{node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_8eadd4dc-90df-4631-844e-8248407c418d_jar]"
		notifies :start, "service[service_8eadd4dc-90df-4631-844e-8248407c418d_jar]"
	end
end

if node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['InstallationDir']}/service-8eadd4dc-90df-4631-844e-8248407c418d.jar" do
		notifies :stop, "service[service_8eadd4dc-90df-4631-844e-8248407c418d_jar]", :immediately
		#notifies :disable, "service[service_8eadd4dc-90df-4631-844e-8248407c418d_jar]", :immediately
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
