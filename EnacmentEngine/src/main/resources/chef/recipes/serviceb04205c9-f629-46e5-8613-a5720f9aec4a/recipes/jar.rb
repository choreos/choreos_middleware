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


service "service_b04205c9-f629-46e5-8613-a5720f9aec4a_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-b04205c9-f629-46e5-8613-a5720f9aec4a.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-b04205c9-f629-46e5-8613-a5720f9aec4a.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-b04205c9-f629-46e5-8613-a5720f9aec4a.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-b04205c9-f629-46e5-8613-a5720f9aec4a.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar" do
    source "#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_b04205c9-f629-46e5-8613-a5720f9aec4a_jar]"
    notifies :start, "service[service_b04205c9-f629-46e5-8613-a5720f9aec4a_jar]"
end

if not node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['deactivate']
	ruby_block "install-file-b04205c9-f629-46e5-8613-a5720f9aec4a" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar" do
    notifies :stop, "service[service_b04205c9-f629-46e5-8613-a5720f9aec4a_jar]", :immediately
    #notifies :disable, "service[service_b04205c9-f629-46e5-8613-a5720f9aec4a_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['deactivate']
	ruby_block "remove-file-b04205c9-f629-46e5-8613-a5720f9aec4a" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir']}/service-b04205c9-f629-46e5-8613-a5720f9aec4a.jar]"
	end
end