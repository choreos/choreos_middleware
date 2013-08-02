# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

service "service_$NAME_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-$NAME.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-$NAME.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-$NAME.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-$NAME.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
    #notifies :disable, "service[service_$NAME_jar]", :immediately
    action :nothing
end

ruby_block "disable-service$NAME" do
	block do
		# do nothing!
		i = 0
	end
	notifies :delete, "file[#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar]", :immediately
end

ruby_block "remove-file-$NAME" do
    block do
    	# do nothing!
    	i = 0
    end
	notifies :stop, "service[service_$NAME_jar]", :immediately
end

bash "edit_json" do
    cwd "#{ENV['HOME']}"
    code "sed -i '/$NAME::remove/d' ./chef-solo/node.json"
end

bash "remove_$NAME_recipe" do
    cwd "#{ENV['HOME']}"
    code "rm -rf chef-solo/cookbooks/$NAME"
end

