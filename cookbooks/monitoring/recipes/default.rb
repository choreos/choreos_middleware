# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

include_recipe "glimpse"

# should be like PlatformMonitoring-version
monitoring_name = "#{node['monitoring']['tarball_name']}-#{node['monitoring']['tarball_version']}"

# should be like $HOME/PlatformMonitoring-version
monitoring_install_path = "#{ENV['HOME']}/#{monitoring_name}"

# monitoring package name
monitoring_tarball_name = "#{ENV['HOME']}/#{monitoring_name}-bin.tar.gz"

# monitoring package url
monitoring_url = "#{node['monitoring']['tarball_server']}/#{monitoring_name}-bin.tar.gz"

# download monitoring tarball
remote_file "#{monitoring_tarball_name}" do
    source "#{monitoring_url}"
        action :create_if_missing
end

home = "#{ENV['HOME']}"

execute 'extract_monitoring_tarball' do
  command "sudo mkdir -p #{monitoring_install_path} ; cd #{ENV['HOME']} ; sudo tar xzf #{monitoring_name}-bin.tar.gz"
  action :run
  not_if { ::File.exists?(monitoring_install_path) }
end

service "#{monitoring_name}" do
        # Dirty trick to save the java pid to a file
        start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/#{monitoring_name}.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/#{monitoring_name}.pid ; exec java -jar #{monitoring_install_path}/#{monitoring_name}.jar \""
        stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/#{monitoring_name}.pid"
        action :start
        status_command "if ps -p `cat /var/run/#{monitoring_name}.pid` > /dev/null  ; then exit 0; else exit 1; fi"
        supports :start => true, :stop => true, :status => true
end

