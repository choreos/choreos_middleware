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


service "service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['InstallationDir']}/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

if not node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['deactivate']
	remote_file "#{node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['InstallationDir']}/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.jar" do
  		source "#{node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['PackageURL']}"
  		action :create_if_missing
		#notifies :enable, "service[service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar]"
		notifies :start, "service[service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar]"
	end
end

if node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['deactivate']
	file "#{node['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['InstallationDir']}/service-14a0b696-fa81-4c14-925b-43a57e3d92c0.jar" do
		notifies :stop, "service[service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar]", :immediately
		#notifies :disable, "service[service_14a0b696-fa81-4c14-925b-43a57e3d92c0_jar]", :immediately
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
