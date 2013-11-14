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


service "service_acaa502d-8a89-46c2-838a-fee975e03cec_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-acaa502d-8a89-46c2-838a-fee975e03cec.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-acaa502d-8a89-46c2-838a-fee975e03cec.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-acaa502d-8a89-46c2-838a-fee975e03cec.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-acaa502d-8a89-46c2-838a-fee975e03cec.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar" do
    source "#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_acaa502d-8a89-46c2-838a-fee975e03cec_jar]"
    notifies :start, "service[service_acaa502d-8a89-46c2-838a-fee975e03cec_jar]"
end

if not node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['deactivate']
	ruby_block "install-file-acaa502d-8a89-46c2-838a-fee975e03cec" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar" do
    notifies :stop, "service[service_acaa502d-8a89-46c2-838a-fee975e03cec_jar]", :immediately
    #notifies :disable, "service[service_acaa502d-8a89-46c2-838a-fee975e03cec_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['deactivate']
	ruby_block "remove-file-acaa502d-8a89-46c2-838a-fee975e03cec" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir']}/service-acaa502d-8a89-46c2-838a-fee975e03cec.jar]"
	end
end