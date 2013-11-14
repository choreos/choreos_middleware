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


service "service_cc85a937-4ef6-43cc-9171-617d06c2a46a_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar" do
    source "#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_cc85a937-4ef6-43cc-9171-617d06c2a46a_jar]"
    notifies :start, "service[service_cc85a937-4ef6-43cc-9171-617d06c2a46a_jar]"
end

if not node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['deactivate']
	ruby_block "install-file-cc85a937-4ef6-43cc-9171-617d06c2a46a" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar" do
    notifies :stop, "service[service_cc85a937-4ef6-43cc-9171-617d06c2a46a_jar]", :immediately
    #notifies :disable, "service[service_cc85a937-4ef6-43cc-9171-617d06c2a46a_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['deactivate']
	ruby_block "remove-file-cc85a937-4ef6-43cc-9171-617d06c2a46a" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar]"
	end
end