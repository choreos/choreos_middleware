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


service "service_ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar" do
    source "#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb_jar]"
    notifies :start, "service[service_ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb_jar]"
end

if not node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['deactivate']
	ruby_block "install-file-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar" do
    notifies :stop, "service[service_ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb_jar]", :immediately
    #notifies :disable, "service[service_ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['deactivate']
	ruby_block "remove-file-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir']}/service-ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb.jar]"
	end
end