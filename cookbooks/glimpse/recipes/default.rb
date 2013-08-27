# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

include_recipe "activemq"

settings_file = "systemSettings"
glimpse_name = "glimpse-manager.0.3"
glimpse_filename = "#{ENV['HOME']}/#{glimpse_name}"
glimpse_tarballname = "#{glimpse_filename}.tar.gz"
glimpse_install_path = "#{glimpse_filename}"

remote_file "#{glimpse_tarballname}" do
    source "#{node['glimpse']['tarball_url']}"
	action :create_if_missing
    not_if { ::File.exists?(glimpse_tarballname) }
end

home = "#{ENV['HOME']}"

execute 'extract_glimpse_tarball' do
    command "sudo mkdir -p #{glimpse_filename}; cd #{ENV['HOME']}; sudo tar xzf #{glimpse_name}.tar.gz"
    action :run
    not_if { ::File.exists?(glimpse_install_path) }
end

drools = "configFiles/droolsFile"

template "#{glimpse_install_path}/#{drools}" do
  source "droolsFile.erb"
  variables({
	:probeTopic => "probeTopic"
  })
end

environment = "configFiles/environmentFile"

template "#{glimpse_install_path}/#{environment}" do
  source "environmentFile.erb"
  variables({
	 :factory_initial => "org.apache.activemq.jndi.ActiveMQInitialContextFactory",
	 :provider_url => "tcp://localhost:61616",
	 :security_principal => "system",
	 :credentials => "manager",
	 :connection_factory => "TopicCF",
	 :service_topic => "jms.serviceTopic",
	 :probe_topic => "jms.probeTopic"
  })
end

manager = "configFiles/managerFile"

template "#{glimpse_install_path}/#{manager}" do
  source "managerFile.erb"
  variables({
     :serviceTopic => "serviceTopic",
	 :probeTopic => "probeTopic",
	 :answerTopic => "answerTopic"
  })
end

template "#{glimpse_install_path}/#{settings_file}" do
  source "systemSettings.erb"
  variables({
	 :environment_file => "#{glimpse_install_path}/#{environment}",
	 :drools_file => "#{glimpse_install_path}/#{drools}",
	 :manager_file => "#{glimpse_install_path}/#{manager}"
  })
end

service "#{glimpse_name}" do
        # Dirty trick to save the java pid to a file
        start_command "start-stop-daemon -b --start --quiet --oknodo --pidfile /var/run/#{glimpse_name}.pid --exec /bin/bash -- -c \"echo \\$\\$ > /var/run/#{glimpse_name}.pid ; exec java -jar #{glimpse_install_path}/glimpse-manager.jar #{glimpse_install_path}/#{settings_file}\""
        stop_command "start-stop-daemon --stop --signal 15 --quiet --oknodo --pidfile /var/run/#{glimpse_name}.pid"
        action :start
        status_command "if ps -p `cat /var/run/#{glimpse_name}.pid` > /dev/null  ; then exit 0; else exit 1; fi"
        supports :start => true, :stop => true, :status => true
end

