# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

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
  command "java -jar #{node['easyesb']['cli']['jar_name']} -s #{node['cd']['$NAME']['xml']} > #{node['cd']['$NAME']['log']}"
  cwd "#{node['easyesb']['cli']['bin_folder']}"
  action :run
end
