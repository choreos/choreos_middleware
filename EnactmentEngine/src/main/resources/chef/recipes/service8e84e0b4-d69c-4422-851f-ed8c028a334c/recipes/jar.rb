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


service "service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['InstallationDir']}/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['Active']
	remote_file "#{node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['InstallationDir']}/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.jar" do
  		source "#{node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar]"
		notifies :start, "service[service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar]"
	end
end

if not node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['Active']
	file "#{node['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['InstallationDir']}/service-8e84e0b4-d69c-4422-851f-ed8c028a334c.jar" do
		notifies :stop, "service[service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar]", :immediately
		#notifies :disable, "service[service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar]", :immediately
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
