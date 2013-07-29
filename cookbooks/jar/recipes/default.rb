# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

include_recipe "apt" # java recipe is failing without recipe apt
include_recipe "java"

remote_file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
    source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
    action :create_if_missing
    #notifies :start, "service[service_$NAME_jar]"
end

service "service_$NAME_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-$NAME.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-$NAME.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-$NAME.pid"
 	action :start
	status_command "if ps -p `cat /var/run/service-$NAME.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

