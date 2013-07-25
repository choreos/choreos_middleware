# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

include_recipe "apt" # java recipe is failing without recipe apt
include_recipe "java"

remote_file "#{node['easyesb']['cli']['downloaded_file']}" do
  source "#{node['easyesb']['cli']['url']}"
  action :create_if_missing
end

execute 'extract_easyesb_client' do
  command "tar -zxf #{node['easyesb']['cli']['downloaded_file']}"
  creates "#{node['easyesb']['cli']['executable_jar']}"
  cwd "#{node['easyesb']['cli']['work_dir']}"
  action :run
end

