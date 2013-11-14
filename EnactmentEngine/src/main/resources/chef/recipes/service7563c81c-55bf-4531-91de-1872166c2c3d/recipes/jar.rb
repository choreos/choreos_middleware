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


service "service_7563c81c-55bf-4531-91de-1872166c2c3d_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-7563c81c-55bf-4531-91de-1872166c2c3d.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-7563c81c-55bf-4531-91de-1872166c2c3d.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['InstallationDir']}/service-7563c81c-55bf-4531-91de-1872166c2c3d.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-7563c81c-55bf-4531-91de-1872166c2c3d.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-7563c81c-55bf-4531-91de-1872166c2c3d.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['Active']
	remote_file "#{node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['InstallationDir']}/service-7563c81c-55bf-4531-91de-1872166c2c3d.jar" do
  		source "#{node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_7563c81c-55bf-4531-91de-1872166c2c3d_jar]"
		notifies :start, "service[service_7563c81c-55bf-4531-91de-1872166c2c3d_jar]"
	end
end

if not node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['Active']
	file "#{node['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['InstallationDir']}/service-7563c81c-55bf-4531-91de-1872166c2c3d.jar" do
		notifies :stop, "service[service_7563c81c-55bf-4531-91de-1872166c2c3d_jar]", :immediately
		#notifies :disable, "service[service_7563c81c-55bf-4531-91de-1872166c2c3d_jar]", :immediately
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
