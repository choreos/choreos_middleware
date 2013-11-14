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
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-$NAME.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-$NAME.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-$NAME.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-$NAME.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
  		source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_$NAME_jar]"
		notifies :start, "service[service_$NAME_jar]"
	end
end

if node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
		notifies :stop, "service[service_$NAME_jar]", :immediately
		#notifies :disable, "service[service_$NAME_jar]", :immediately
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
