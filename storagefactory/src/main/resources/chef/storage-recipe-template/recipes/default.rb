#
# Cookbook Name:: choreos-create-database
# Recipe:: default
#
# Copyright 2010, Opscode, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
#   All ocurrences of '$ UUID' must be replaced with the actual storage  #
#        UUID before uploading the recipe to the chef-server             #             
#									 #
##########################################################################

include_recipe "mysql::server"

template "#{node['storage']['$UUID']['sqlscript']}" do
  source "create-database.sql.erb"
  mode "0644"
end

execute "mysql-install-privileges" do
  command "/usr/bin/mysql -u root #{node['mysql']['server_root_password'].empty? ? '' : '-p' }#{node['mysql']['server_root_password']} < #{node['storage']['$UUID']['sqlscript']}"
  action :nothing
  subscribes :run, resources("template[#{node['storage']['$UUID']['sqlscript']}]"), :immediately
end
