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


service "service_6a9954f7-7de3-42c9-90e9-2376f4f098e5_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar" do
    source "#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_6a9954f7-7de3-42c9-90e9-2376f4f098e5_jar]"
    notifies :start, "service[service_6a9954f7-7de3-42c9-90e9-2376f4f098e5_jar]"
end

if not node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['deactivate']
	ruby_block "install-file-6a9954f7-7de3-42c9-90e9-2376f4f098e5" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar" do
    notifies :stop, "service[service_6a9954f7-7de3-42c9-90e9-2376f4f098e5_jar]", :immediately
    #notifies :disable, "service[service_6a9954f7-7de3-42c9-90e9-2376f4f098e5_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['deactivate']
	ruby_block "remove-file-6a9954f7-7de3-42c9-90e9-2376f4f098e5" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir']}/service-6a9954f7-7de3-42c9-90e9-2376f4f098e5.jar]"
	end
end