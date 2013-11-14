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


service "service_da66e508-c56a-402d-9f6c-1ddc2a26d0e8_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar" do
    source "#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_da66e508-c56a-402d-9f6c-1ddc2a26d0e8_jar]"
    notifies :start, "service[service_da66e508-c56a-402d-9f6c-1ddc2a26d0e8_jar]"
end

if not node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['deactivate']
	ruby_block "install-file-da66e508-c56a-402d-9f6c-1ddc2a26d0e8" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar" do
    notifies :stop, "service[service_da66e508-c56a-402d-9f6c-1ddc2a26d0e8_jar]", :immediately
    #notifies :disable, "service[service_da66e508-c56a-402d-9f6c-1ddc2a26d0e8_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['deactivate']
	ruby_block "remove-file-da66e508-c56a-402d-9f6c-1ddc2a26d0e8" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir']}/service-da66e508-c56a-402d-9f6c-1ddc2a26d0e8.jar]"
	end
end