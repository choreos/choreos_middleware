#
# Cookbook Name:: easyesb
# Recipe:: default
#
# Deploys a CD on an EasyESB node
#
# Copyright 2011, USP
#
# LGPL 2.0 or, at your option, any later version
#

include_recipe "easyesb::default" # java recipe is failing without recipe apt
include_recipe "easyesb::client"

remote_file "#{node['cd']['downloaded_file']}" do
  source "#{node['cd']['url']}"
  action :create_if_missing
end

execute 'extract_cd' do
  command "tar -zxf #{node['cd']['downloaded_file']}"
  creates "#{node['cd']['xml']}"
  cwd "#{node['cd']['work_dir']}"
  action :run
end

execute "deploy_cd" do
  command "java -jar #{node['easyesb']['cli']['jar_name']} -c #{node['easyesb']['admin_endpoint']} -s #{node['cd']['xml']}"
  cwd "#{node['easyesb']['cli']['bin_folder']}"
  action :run
end
