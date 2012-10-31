#
# Cookbook Name:: cd
# Recipe:: default
#
# Deploys a CD on an EasyESB node
#
# Copyright 2012, USP
#
# Author: Leonardo Leite
# License: LGPL 2.0 or, at your option, any later version
#

include_recipe "easyesb::default" 
include_recipe "easyesb::client"

remote_file "#{node['cd']['$NAME']['downloaded_file']}" do
  source "#{node['cd']['$NAME']['url']}"
  action :create_if_missing
end

execute 'extract_cd' do
  command "tar -zxf #{node['cd']['$NAME']['downloaded_file']}"
  creates "#{node['cd']['$NAME']['xml']}"
  cwd "#{node['cd']['$NAME']['work_dir']}"
  action :run
end

execute "deploy_cd" do
  command "java -jar #{node['easyesb']['cli']['jar_name']} -c #{node['easyesb']['admin_endpoint']} -s #{node['cd']['$NAME']['xml']}"
  cwd "#{node['easyesb']['cli']['bin_folder']}"
  action :run
end
