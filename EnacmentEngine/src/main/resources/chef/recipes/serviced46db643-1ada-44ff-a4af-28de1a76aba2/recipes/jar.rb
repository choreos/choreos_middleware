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


service "service_d46db643-1ada-44ff-a4af-28de1a76aba2_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-d46db643-1ada-44ff-a4af-28de1a76aba2.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-d46db643-1ada-44ff-a4af-28de1a76aba2.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-d46db643-1ada-44ff-a4af-28de1a76aba2.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-d46db643-1ada-44ff-a4af-28de1a76aba2.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar" do
    source "#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_d46db643-1ada-44ff-a4af-28de1a76aba2_jar]"
    notifies :start, "service[service_d46db643-1ada-44ff-a4af-28de1a76aba2_jar]"
end

if not node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['deactivate']
	ruby_block "install-file-d46db643-1ada-44ff-a4af-28de1a76aba2" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar" do
    notifies :stop, "service[service_d46db643-1ada-44ff-a4af-28de1a76aba2_jar]", :immediately
    #notifies :disable, "service[service_d46db643-1ada-44ff-a4af-28de1a76aba2_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['deactivate']
	ruby_block "remove-file-d46db643-1ada-44ff-a4af-28de1a76aba2" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar]"
	end
end