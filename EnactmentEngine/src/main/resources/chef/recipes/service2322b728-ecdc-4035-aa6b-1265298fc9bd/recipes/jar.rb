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


service "service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['InstallationDir']}/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['InstallationDir']}/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.jar" do
  		source "#{node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar]"
		notifies :start, "service[service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar]"
	end
end

if node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['InstallationDir']}/service-2322b728-ecdc-4035-aa6b-1265298fc9bd.jar" do
		notifies :stop, "service[service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar]", :immediately
		#notifies :disable, "service[service_2322b728-ecdc-4035-aa6b-1265298fc9bd_jar]", :immediately
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
