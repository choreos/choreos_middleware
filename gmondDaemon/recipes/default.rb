#
# Cookbook Name:: gmondDaemon
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

include_recipe "ganglia"

execute "set_hostname" do
  command "gmetric --name hostname --value #{node['hostname']} --type string"
end
