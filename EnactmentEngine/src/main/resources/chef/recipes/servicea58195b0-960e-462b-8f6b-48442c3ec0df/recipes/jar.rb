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


service "service_a58195b0-960e-462b-8f6b-48442c3ec0df_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-a58195b0-960e-462b-8f6b-48442c3ec0df.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-a58195b0-960e-462b-8f6b-48442c3ec0df.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-a58195b0-960e-462b-8f6b-48442c3ec0df.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-a58195b0-960e-462b-8f6b-48442c3ec0df.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar" do
    source "#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_a58195b0-960e-462b-8f6b-48442c3ec0df_jar]"
    notifies :start, "service[service_a58195b0-960e-462b-8f6b-48442c3ec0df_jar]"
end

if not node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['deactivate']
	ruby_block "install-file-a58195b0-960e-462b-8f6b-48442c3ec0df" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar" do
    notifies :stop, "service[service_a58195b0-960e-462b-8f6b-48442c3ec0df_jar]", :immediately
    #notifies :disable, "service[service_a58195b0-960e-462b-8f6b-48442c3ec0df_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['deactivate']
	ruby_block "remove-file-a58195b0-960e-462b-8f6b-48442c3ec0df" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir']}/service-a58195b0-960e-462b-8f6b-48442c3ec0df.jar]"
	end
end