#
# Cookbook Name:: tomcat 
# Recipe:: choreos
#
# Deploys Tomcat with several useful libs
#
# Copyright 2012, USP
#
# LGPL 2.0 or, at your option, any later version
# Leonardo Leite, Eduardo Hideo, Carlos Eduardo M. Santos

include_recipe "tomcat::default"

remote_file "#{node['tomcat']['lib_dir']}/#{node['tomcat']['libs_file']}" do
  source "#{node['tomcat']['libs_url']}"
  action :create
end

execute 'extract_libs' do
  command "tar -xzf #{node['tomcat']['libs_file']}"
#  creates "#{node['tomcat']['lib_dir']}/gmbal-api-only-3.1.0-b001.jar"
  cwd "#{node['tomcat']['lib_dir']}"
  action :run
end

execute 'set_permissions' do
  command "chmod o+r #{node['tomcat']['lib_dir']}/*.jar"
  action :run
  notifies :restart, resources(:service => "tomcat")
end
