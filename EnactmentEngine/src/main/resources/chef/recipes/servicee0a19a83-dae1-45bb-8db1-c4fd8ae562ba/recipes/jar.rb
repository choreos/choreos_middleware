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


service "service_e0a19a83-dae1-45bb-8db1-c4fd8ae562ba_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar" do
    source "#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_e0a19a83-dae1-45bb-8db1-c4fd8ae562ba_jar]"
    notifies :start, "service[service_e0a19a83-dae1-45bb-8db1-c4fd8ae562ba_jar]"
end

if not node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['deactivate']
	ruby_block "install-file-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar" do
    notifies :stop, "service[service_e0a19a83-dae1-45bb-8db1-c4fd8ae562ba_jar]", :immediately
    #notifies :disable, "service[service_e0a19a83-dae1-45bb-8db1-c4fd8ae562ba_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['deactivate']
	ruby_block "remove-file-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir']}/service-e0a19a83-dae1-45bb-8db1-c4fd8ae562ba.jar]"
	end
end