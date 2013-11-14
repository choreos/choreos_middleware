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


service "service_97aaa8db-f1c2-4ba3-964b-bace8e71fd4b_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar" do
    source "#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_97aaa8db-f1c2-4ba3-964b-bace8e71fd4b_jar]"
    notifies :start, "service[service_97aaa8db-f1c2-4ba3-964b-bace8e71fd4b_jar]"
end

if not node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['deactivate']
	ruby_block "install-file-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar" do
    notifies :stop, "service[service_97aaa8db-f1c2-4ba3-964b-bace8e71fd4b_jar]", :immediately
    #notifies :disable, "service[service_97aaa8db-f1c2-4ba3-964b-bace8e71fd4b_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['deactivate']
	ruby_block "remove-file-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir']}/service-97aaa8db-f1c2-4ba3-964b-bace8e71fd4b.jar]"
	end
end