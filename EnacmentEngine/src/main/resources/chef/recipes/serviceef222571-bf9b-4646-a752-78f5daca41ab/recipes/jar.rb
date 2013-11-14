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


service "service_ef222571-bf9b-4646-a752-78f5daca41ab_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-ef222571-bf9b-4646-a752-78f5daca41ab.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-ef222571-bf9b-4646-a752-78f5daca41ab.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-ef222571-bf9b-4646-a752-78f5daca41ab.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-ef222571-bf9b-4646-a752-78f5daca41ab.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar" do
    source "#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_ef222571-bf9b-4646-a752-78f5daca41ab_jar]"
    notifies :start, "service[service_ef222571-bf9b-4646-a752-78f5daca41ab_jar]"
end

if not node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['deactivate']
	ruby_block "install-file-ef222571-bf9b-4646-a752-78f5daca41ab" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar" do
    notifies :stop, "service[service_ef222571-bf9b-4646-a752-78f5daca41ab_jar]", :immediately
    #notifies :disable, "service[service_ef222571-bf9b-4646-a752-78f5daca41ab_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['deactivate']
	ruby_block "remove-file-ef222571-bf9b-4646-a752-78f5daca41ab" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar]"
	end
end