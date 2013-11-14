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


service "service_da7186c4-a6b1-46d2-b2b6-7c7ed785c953_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar" do
    source "#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_da7186c4-a6b1-46d2-b2b6-7c7ed785c953_jar]"
    notifies :start, "service[service_da7186c4-a6b1-46d2-b2b6-7c7ed785c953_jar]"
end

if not node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['deactivate']
	ruby_block "install-file-da7186c4-a6b1-46d2-b2b6-7c7ed785c953" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar" do
    notifies :stop, "service[service_da7186c4-a6b1-46d2-b2b6-7c7ed785c953_jar]", :immediately
    #notifies :disable, "service[service_da7186c4-a6b1-46d2-b2b6-7c7ed785c953_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['deactivate']
	ruby_block "remove-file-da7186c4-a6b1-46d2-b2b6-7c7ed785c953" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir']}/service-da7186c4-a6b1-46d2-b2b6-7c7ed785c953.jar]"
	end
end