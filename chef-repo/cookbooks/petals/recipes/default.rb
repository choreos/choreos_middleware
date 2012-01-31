#
# Cookbook Name:: petals
# Recipe:: default
#
# Copyright 2011, USP
#
# LGPL 2.0 or, at your option, any later version
#

# If there is no master, I'll be the one.
# Run at the very beginning to minimize race condition problem.
masters = search(:node, 'container_type:master')
myType = node['petals']['container_type']

if masters.empty? and myType == 'slave'
  myType = 'master'
  master = node
elsif not masters.empty? and masters[0]['ipaddress'] != node['ipaddress']
  myType = 'slave'
  master = masters[0]
end

if node['petals']['container_type'] != myType
  node['petals']['container_type'] = myType
end

node.save

# Installing java and configuring JAVA_HOME
# This is run before other packages are installed to update apt database
include_recipe 'java::sun'

ENV['JAVA_HOME'] = '/usr/lib/jvm/java-6-sun'

template "/etc/profile.d/java.sh" do
  source "etc/profile.d/java.sh.erb"
  variables( :JAVA_HOME => ENV['JAVA_HOME'] )
  mode 0644
  action :create_if_missing
end

# MySQL database
include_recipe 'mysql::server'

mysql_database "creates petals database" do
  host "localhost"
  username "root"
  password node[:mysql][:server_root_password]
  database "petals"
  action :create_db
end

DOWNLOAD_SERVER = "http://valinhos.ime.usp.br:54080/demo"

def download(filename)
  diskFile = "/tmp/#{filename}"
  webFile = "#{DOWNLOAD_SERVER}/#{filename}"

  remote_file diskFile do
    source webFile
    action :create_if_missing
  end
end

# Download and unzip
package 'unzip' do
  action :install
end

filename = 'dsb-fulldistribution-latest.zip'
download filename
execute 'extract petals' do
  command "unzip /tmp/#{filename} -d /opt"
  creates node['petals']['dir']
  action :run
end

# Configure
template "#{node['petals']['dir']}/conf/server.properties" do
  source "server.properties.erb"
  owner "root"
  group "root"
  mode "0644"
end

bash 'fix installs on startup' do
  user 'root'
  cwd "#{node['petals']['dir']}/conf"
  code <<-EOH
  sed -i 's/embedded.component.list=.*/embedded.component.list=/' dsb.cfg
  EOH
end

bash 'shorten topology timeouts' do
  user 'root'
  cwd "#{node['petals']['dir']}/conf"
  code <<-EOH
  sed -i 's/topology.update.period=.*/topology.update.period=10/' server.properties
  sed -i 's/registry.synchro.period=.*/registry.synchro.period=30/' server.properties
  EOH
end

bash 'fix permissions' do
  user 'root'
  cwd "#{node['petals']['dir']}/bin"
  code <<-EOH
  /bin/chmod 755 *.sh
  EOH
end

# Configure topology
template "#{node['petals']['dir']}/conf/topology.xml" do
  source "topology.xml.erb"
  owner "root"
  group "root"
  mode "0644"
  variables({:master => master})
end

# Start petals
service 'petals' do
  start_command "#{node['petals']['dir']}/bin/startup.sh -D"
  stop_command "#{node['petals']['dir']}/bin/stop.sh"
  supports :start => true, :stop => true
  action [ :start ]
  notifies :run, 'bash[wait petals]', :immediately
end
 
bash 'wait petals' do
  cwd "#{node['petals']['dir']}/logs"
  code <<-EOH
  echo 'Waiting for petals by monitoring its log...'
  last_log=$(ls -tr *.log | tail -n 1)
  >$last_log
  while ! grep -q 'Time to look for new services to expose' $last_log
  do
    sleep 0.5
  done
  EOH
  action :nothing
end

# Install components
filename = 'components.tar.gz'
download filename

execute "extract components" do
  command "tar xzf /tmp/#{filename} -C /tmp/"
  creates "/tmp/components"
  action :run
end

execute "install components" do
  command "cp /tmp/components/* #{node['petals']['dir']}/install"
  creates "#{node['petals']['dir']}/installed/petals-bc-ejb-1.3.zip"
  action :run
  notifies :run, 'bash[wait petals]', :immediately
end
