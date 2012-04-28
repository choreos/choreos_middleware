#
# Cookbook Name:: petals
# Recipe:: default
#
# Copyright 2012, USP
#
# LGPL 2.0 or, at your option, any later version
#

# This recipe doesn't care about topology.xml
# and doesn't start petals

# Installing java and configuring JAVA_HOME
# This is run before other packages are installed to update apt database
include_recipe 'java::openjdk'

ENV['JAVA_HOME'] = '/usr/lib/jvm/java-6-openjdk'

template "/etc/profile.d/java.sh" do
  source "etc/profile.d/java.sh.erb"
  variables( :JAVA_HOME => ENV['JAVA_HOME'] )
  mode 0644
  action :create_if_missing
end

DOWNLOAD_SERVER = "http://valinhos.ime.usp.br:54080/demo2"

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
