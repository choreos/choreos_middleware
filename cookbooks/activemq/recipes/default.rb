#
# Cookbook Name:: activemq
# Recipe:: default
#
# Copyright 2009, Opscode, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

include_recipe "java"

case node[:platform]
when "ubuntu", "debian"

# Install activemq
package "activemq" do
	action :install
end

# Create choreos broker file
execute "mkdir -p /etc/activemq/instances-available/choreos" do
	not_if { ::File.exists?("/etc/activemq/instances-available/choreos") }
end

template "/etc/activemq/instances-available/choreos/activemq.xml" do
	mode 0755
	source "choreos.activemq.erb"
	variables({
		:ip=> "0.0.0.0"
	})
end

template "/etc/activemq/instances-available/choreos/log4j.properties" do
	mode 0755
	source "choreos.log4j.erb"
end

# Enable choreos broker file
execute "broker-symlink" do
	command "sudo ln -s /etc/activemq/instances-available/choreos/ /etc/activemq/instances-enabled/choreos; sudo service activemq restart"
	action :run
	not_if { ::File.exists?("/etc/activemq/instances-enabled/choreos") }
end

else

# It is not working on Ubuntu 12.04
tmp = Chef::Config[:file_cache_path]
version = node['activemq']['version']
mirror = node['activemq']['mirror']
activemq_home = "#{node['activemq']['home']}/apache-activemq-#{version}"

directory node['activemq']['home'] do
  recursive true
end

unless File.exists?("#{activemq_home}/bin/activemq")
  remote_file "#{tmp}/apache-activemq-#{version}-bin.tar.gz" do
    source "#{mirror}/activemq/apache-activemq/#{version}/apache-activemq-#{version}-bin.tar.gz"
    mode "0644"
  end

  execute "tar zxf #{tmp}/apache-activemq-#{version}-bin.tar.gz" do
    cwd node['activemq']['home']
  end
end

file "#{activemq_home}/bin/activemq" do
  owner "root"
  group "root"
  mode "0755"
end

# TODO: make this more robust
arch = (node['kernel']['machine'] == "x86_64") ? "x86-64" : "x86-32"

link "/etc/init.d/activemq" do
  to "#{activemq_home}/bin/linux-#{arch}/activemq"
end

service "activemq" do
  supports  :restart => true, :status => true
  action [:enable, :start]
end

# symlink so the default wrapper.conf can find the native wrapper library
link "#{activemq_home}/bin/linux" do
  to "#{activemq_home}/bin/linux-#{arch}"
end

# symlink the wrapper's pidfile location into /var/run
link "/var/run/activemq.pid" do
  to "#{activemq_home}/bin/linux/ActiveMQ.pid"
  not_if "test -f /var/run/activemq.pid"
end

template "#{activemq_home}/bin/linux/wrapper.conf" do
  source "wrapper.conf.erb"
  mode 0644
  notifies :restart, 'service[activemq]'
end

end

