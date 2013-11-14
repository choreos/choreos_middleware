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


service "service_6f15e032-d545-4ee9-85cc-df9897a07d6e_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar" do
    source "#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_6f15e032-d545-4ee9-85cc-df9897a07d6e_jar]"
    notifies :start, "service[service_6f15e032-d545-4ee9-85cc-df9897a07d6e_jar]"
end

if not node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['deactivate']
	ruby_block "install-file-6f15e032-d545-4ee9-85cc-df9897a07d6e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar" do
    notifies :stop, "service[service_6f15e032-d545-4ee9-85cc-df9897a07d6e_jar]", :immediately
    #notifies :disable, "service[service_6f15e032-d545-4ee9-85cc-df9897a07d6e_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['deactivate']
	ruby_block "remove-file-6f15e032-d545-4ee9-85cc-df9897a07d6e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir']}/service-6f15e032-d545-4ee9-85cc-df9897a07d6e.jar]"
	end
end