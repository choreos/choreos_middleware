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


service "service_7a253929-9744-465c-93c9-350a553ec236_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-7a253929-9744-465c-93c9-350a553ec236.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-7a253929-9744-465c-93c9-350a553ec236.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-7a253929-9744-465c-93c9-350a553ec236.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-7a253929-9744-465c-93c9-350a553ec236.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar" do
    source "#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_7a253929-9744-465c-93c9-350a553ec236_jar]"
    notifies :start, "service[service_7a253929-9744-465c-93c9-350a553ec236_jar]"
end

if not node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['deactivate']
	ruby_block "install-file-7a253929-9744-465c-93c9-350a553ec236" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar" do
    notifies :stop, "service[service_7a253929-9744-465c-93c9-350a553ec236_jar]", :immediately
    #notifies :disable, "service[service_7a253929-9744-465c-93c9-350a553ec236_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['deactivate']
	ruby_block "remove-file-7a253929-9744-465c-93c9-350a553ec236" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar]"
	end
end