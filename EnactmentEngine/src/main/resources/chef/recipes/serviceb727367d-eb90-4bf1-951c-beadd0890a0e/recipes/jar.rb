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


service "service_b727367d-eb90-4bf1-951c-beadd0890a0e_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-b727367d-eb90-4bf1-951c-beadd0890a0e.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-b727367d-eb90-4bf1-951c-beadd0890a0e.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-b727367d-eb90-4bf1-951c-beadd0890a0e.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-b727367d-eb90-4bf1-951c-beadd0890a0e.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar" do
    source "#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_b727367d-eb90-4bf1-951c-beadd0890a0e_jar]"
    notifies :start, "service[service_b727367d-eb90-4bf1-951c-beadd0890a0e_jar]"
end

if not node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['deactivate']
	ruby_block "install-file-b727367d-eb90-4bf1-951c-beadd0890a0e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar" do
    notifies :stop, "service[service_b727367d-eb90-4bf1-951c-beadd0890a0e_jar]", :immediately
    #notifies :disable, "service[service_b727367d-eb90-4bf1-951c-beadd0890a0e_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['deactivate']
	ruby_block "remove-file-b727367d-eb90-4bf1-951c-beadd0890a0e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir']}/service-b727367d-eb90-4bf1-951c-beadd0890a0e.jar]"
	end
end