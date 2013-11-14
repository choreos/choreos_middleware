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


service "service_f10b86ff-43bb-48fa-a3cd-4a7499cde3ea_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar" do
    source "#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_f10b86ff-43bb-48fa-a3cd-4a7499cde3ea_jar]"
    notifies :start, "service[service_f10b86ff-43bb-48fa-a3cd-4a7499cde3ea_jar]"
end

if not node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['deactivate']
	ruby_block "install-file-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar" do
    notifies :stop, "service[service_f10b86ff-43bb-48fa-a3cd-4a7499cde3ea_jar]", :immediately
    #notifies :disable, "service[service_f10b86ff-43bb-48fa-a3cd-4a7499cde3ea_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['deactivate']
	ruby_block "remove-file-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar]"
	end
end