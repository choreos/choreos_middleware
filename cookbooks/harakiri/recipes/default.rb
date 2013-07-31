#
# Cookbook Name:: harakiri
# Recipe:: default
#
# Copyright 2013, IME USP
# Thiago Furtado
#

boot_minute_str = `who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'`.strip
boot_minute = boot_minute_str.to_i()

current_minute_str = `date +%M`.strip
current_minute = current_minute.to_i()

h_m = boot_minute - 3
harakiri_minute = boot_minute - 3
if harakiri_minute < 0
  harakiri_minute = harakiri_minute + 60
end

# who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'
cron "run_chef_solo" do
  action :create
  hour "*"
  minute harakiri_minute
  command "chef-solo -c #{ENV['HOME']}/chef-solo/solo.rb"
end

#UUID = /\A([0-9a-fA-F]{32}|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})\z/

http_request "http_delete" do
  action :nothing
  message ""
  url "#node{['CHOReOSData']['nodeData']['deploymentManagerURL']}/nodes/#node{['CHOReOSData']['nodeData']['nodeID']}"
  only_if do
    File.readlines("#{ENV['HOME']}/chef-solo/node.json").grep(/\A([0-9a-fA-F]{32}|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})\z/).any? and current_minute >= h_m and current_minute < boot_minute
  end
end

bash "test_who" do
  cwd "/tmp"
  code <<-EOF
    echo #{boot_minute_str} >> testeeee
  EOF
  only_if do
    File.readlines("#{ENV['HOME']}/chef-solo/node.json").grep(/\A([0-9a-fA-F]{32}|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})\z/).any? and current_minute >= h_m and current_minute < boot_minute
  end
end
