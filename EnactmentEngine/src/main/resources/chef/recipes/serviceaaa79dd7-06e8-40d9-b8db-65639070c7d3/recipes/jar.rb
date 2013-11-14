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


service "service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['InstallationDir']}/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['InstallationDir']}/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.jar" do
  		source "#{node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar]"
		notifies :start, "service[service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar]"
	end
end

if node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['InstallationDir']}/service-aaa79dd7-06e8-40d9-b8db-65639070c7d3.jar" do
		notifies :stop, "service[service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar]", :immediately
		#notifies :disable, "service[service_aaa79dd7-06e8-40d9-b8db-65639070c7d3_jar]", :immediately
		action :delete
	end
end




# As the last resource in the dnsserver::remove_slave recipe, assuming that the remove_slave
# "undoes" a dnsserver slave installation of some kind, without knowing what that might have been.
#ruby_block "remove_this_recipe" do
#  block do
#    node.run_list.remove("recipe[dnsserver::remove_slave]") if node.run_list.include?("recipe[dnsserver::remove_slave]")
#  end
#  action :nothing
#end
