#
# Cookbook Name:: easyesb
# Recipe:: default
#
# Copyright 2011, USP
#
# LGPL 2.0 or, at your option, any later version
#

include_recipe "apt" # java recipe is failing without recipe apt
include_recipe "java"

remote_file "#{node['easyesb']['downloaded_file']}" do
  source "#{node['easyesb']['url']}"
  action :create_if_missing
end

execute 'extract_easyesb' do
  command "tar -zxf #{node['easyesb']['downloaded_file']}"
  creates "#{node['easyesb']['executable_jar']}"
  cwd "#{node['easyesb']['work_dir']}"
  action :run
end

service "start_easyesb" do
  start_command "cd #{node['easyesb']['bin_folder']} ; java -jar #{node['easyesb']['jar_name']} &"
  action [ :start ]
end
