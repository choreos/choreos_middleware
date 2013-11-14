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


service "service_5f436c2e-527d-4cea-8c69-299a2f3b6fed_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar" do
    source "#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_5f436c2e-527d-4cea-8c69-299a2f3b6fed_jar]"
    notifies :start, "service[service_5f436c2e-527d-4cea-8c69-299a2f3b6fed_jar]"
end

if not node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['deactivate']
	ruby_block "install-file-5f436c2e-527d-4cea-8c69-299a2f3b6fed" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar" do
    notifies :stop, "service[service_5f436c2e-527d-4cea-8c69-299a2f3b6fed_jar]", :immediately
    #notifies :disable, "service[service_5f436c2e-527d-4cea-8c69-299a2f3b6fed_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['deactivate']
	ruby_block "remove-file-5f436c2e-527d-4cea-8c69-299a2f3b6fed" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir']}/service-5f436c2e-527d-4cea-8c69-299a2f3b6fed.jar]"
	end
end