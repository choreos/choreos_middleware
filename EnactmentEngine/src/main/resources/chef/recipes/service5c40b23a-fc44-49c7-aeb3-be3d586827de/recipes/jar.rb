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


service "service_5c40b23a-fc44-49c7-aeb3-be3d586827de_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar" do
    source "#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_5c40b23a-fc44-49c7-aeb3-be3d586827de_jar]"
    notifies :start, "service[service_5c40b23a-fc44-49c7-aeb3-be3d586827de_jar]"
end

if not node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['deactivate']
	ruby_block "install-file-5c40b23a-fc44-49c7-aeb3-be3d586827de" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar" do
    notifies :stop, "service[service_5c40b23a-fc44-49c7-aeb3-be3d586827de_jar]", :immediately
    #notifies :disable, "service[service_5c40b23a-fc44-49c7-aeb3-be3d586827de_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['deactivate']
	ruby_block "remove-file-5c40b23a-fc44-49c7-aeb3-be3d586827de" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['5c40b23a-fc44-49c7-aeb3-be3d586827de']['InstallationDir']}/service-5c40b23a-fc44-49c7-aeb3-be3d586827de.jar]"
	end
end