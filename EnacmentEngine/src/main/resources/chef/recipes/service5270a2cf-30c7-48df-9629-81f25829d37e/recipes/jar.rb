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


service "service_5270a2cf-30c7-48df-9629-81f25829d37e_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-5270a2cf-30c7-48df-9629-81f25829d37e.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-5270a2cf-30c7-48df-9629-81f25829d37e.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-5270a2cf-30c7-48df-9629-81f25829d37e.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-5270a2cf-30c7-48df-9629-81f25829d37e.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar" do
    source "#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_5270a2cf-30c7-48df-9629-81f25829d37e_jar]"
    notifies :start, "service[service_5270a2cf-30c7-48df-9629-81f25829d37e_jar]"
end

if not node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['deactivate']
	ruby_block "install-file-5270a2cf-30c7-48df-9629-81f25829d37e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar" do
    notifies :stop, "service[service_5270a2cf-30c7-48df-9629-81f25829d37e_jar]", :immediately
    #notifies :disable, "service[service_5270a2cf-30c7-48df-9629-81f25829d37e_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['deactivate']
	ruby_block "remove-file-5270a2cf-30c7-48df-9629-81f25829d37e" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir']}/service-5270a2cf-30c7-48df-9629-81f25829d37e.jar]"
	end
end