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
  notifies :create, "ruby_block[set_hostname_once]", :immediately
  not_if { node.attribute?("hostname_was_set") }
end
 
ruby_block "set_hostname_once" do
  block do
    node.set['hostname_was_set'] = true
    node.save
  end
  action :nothing
end
