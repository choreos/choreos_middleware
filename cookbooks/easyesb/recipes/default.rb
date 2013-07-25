# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

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

bash "edit_config" do
  cwd "#{node['easyesb']['bin_folder']}"
  code <<-EOH
    IP=`ifconfig eth0 | grep 'inet addr' | awk -F: '{print $2}' | awk '{print $1}'`
    sed -i s/localhost/$IP/ config.properties
  EOH
end

service "start_easyesb" do
  start_command "cd #{node['easyesb']['bin_folder']} ; java -jar #{node['easyesb']['jar_name']} &"
  action [ :start ]
end

script "wait_easyesb_start" do
  interpreter "bash"
  code <<-EOH
  echo "Waiting for EasyESB start by monitoring its log: #{node['easyesb']['log_file']}"
  message='<sequence>18</sequence>'
  while ! cat #{node['easyesb']['log_file']} | grep -q "$message"
  do
    sleep 1
  done
  echo 'EasyESB has started'
  EOH
  action :run
end


