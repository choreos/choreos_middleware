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


service "service_02354988-5f1e-4b9b-a4b6-d6cda6de6ee3_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar" do
    source "#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_02354988-5f1e-4b9b-a4b6-d6cda6de6ee3_jar]"
    notifies :start, "service[service_02354988-5f1e-4b9b-a4b6-d6cda6de6ee3_jar]"
end

if not node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['deactivate']
	ruby_block "install-file-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar" do
    notifies :stop, "service[service_02354988-5f1e-4b9b-a4b6-d6cda6de6ee3_jar]", :immediately
    #notifies :disable, "service[service_02354988-5f1e-4b9b-a4b6-d6cda6de6ee3_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['deactivate']
	ruby_block "remove-file-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar]"
	end
end