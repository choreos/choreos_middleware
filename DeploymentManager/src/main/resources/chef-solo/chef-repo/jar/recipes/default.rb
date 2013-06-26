
# NOTE: this is a template file. $variables must be properly replaced.

include_recipe "apt" # java recipe is failing without recipe apt
include_recipe "java"


service "service_$NAME_jar" do
	# Dirty trick to save the java pid to a file
 	start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/service-$NAME.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/service-$NAME.pid ; exec java -jar #{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar\""
 	stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/service-$NAME.pid"
 	action :nothing
	status_command "if ps -p `cat /var/run/service-$NAME.pid` > /dev/null  ; then exit 0; else exit 1; fi"
 	supports :start => true, :stop => true, :status => true
end

remote_file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
    source "#{node['CHOReOSData']['serviceData']['$NAME']['PackageURL']}"
    action :nothing
    #notifies :enable, "service[service_$NAME_jar]"
    notifies :start, "service[service_$NAME_jar]"
end

if not node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	ruby_block "install-file-$NAME" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :create_if_missing, "remote_file[#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar]"
	end
end

file "#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar" do
    notifies :stop, "service[service_$NAME_jar]", :immediately
    #notifies :disable, "service[service_$NAME_jar]", :immediately
    action :nothing
end

if node['CHOReOSData']['serviceData']['$NAME']['deactivate']
	ruby_block "remove-file-$NAME" do
	    block do
	    	# do nothing!
	    	i = 0
	    end
		notifies :delete, "remote_file[#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar]"
	end
end
