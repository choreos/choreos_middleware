#
# Cookbook Name:: petals
# Recipe:: default
#
# Copyright 2011, USP
#
# LGPL 2.0 or, at your option, any later version
#
include_recipe "java"
include_recipe "mysql::server"

ZIP_FILE = "petals-platform-3.1.1.zip"
PETALS_URL = "http://maven.ow2.org/maven2/org/ow2/petals/petals-platform/3.1.1/#{ZIP_FILE}"

#download petals zip file
remote_file "#{node['petals']['install_dir']}/#{ZIP_FILE}" do
  source PETALS_URL
  action :nothing
end

#only if it has changed
http_request "HEAD #{PETALS_URL}" do
  message ""
  url PETALS_URL
  action :head
  if File.exists?("#{node['petals']['install_dir']}/#{ZIP_FILE}")
    headers "If-Modified-Since" => File.mtime("#{node['petals']['install_dir']}/#{ZIP_FILE}").httpdate
  end
  notifies :create, resources(:remote_file => "#{node['petals']['install_dir']}/#{ZIP_FILE}"), :immediately
end

package "unzip" do
  action :install
end

#unzip petals
execute "unzip" do
  command "unzip #{node['petals']['install_dir']}/#{ZIP_FILE} -d #{node['petals']['install_dir']}"
  creates "#{node['petals']['install_dir']}/#{ZIP_FILE.gsub('.zip', '')}"
  action :run
end

template "#{node['petals']['install_dir']}/#{ZIP_FILE.gsub('.zip', '')}/conf/server.properties" do
  source "server.properties.erb"
  owner "root"
  group "root"
  mode "0644"
end

@@master = node

search(:node, 'role:petals') do |n|
  if n['petals']['container_type'] == "master"
    @@master = n
    if n.name != node.name
      node.set['petals']['container_type'] = "slave"
    end
  end
end

template "#{node['petals']['install_dir']}/#{ZIP_FILE.gsub('.zip', '')}/conf/topology.xml" do
  source "topology.xml.erb"
  owner "root"
  group "root"
  mode "0644"
  variables({:master => @@master})
end


mysql_database "creates petals database" do
  host "localhost"
  username "root"
  password node[:mysql][:server_root_password]
  database "petals"
  action :create_db
end



