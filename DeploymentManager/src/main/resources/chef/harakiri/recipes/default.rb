#
# Cookbook Name:: harakiri
# Recipe:: default
#
# Copyright 2013, IME USP
# Thiago Furtado
#

b_minute_str = `who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'`.strip
b_minute = b_minute_str.to_i()
boot_minute_str = b_minute.to_i()

current_minute_str = `date +%M`.strip
current_minute = current_minute_str.to_i()

boot_minute = boot_minute_str.to_i() - 2
if boot_minute < 0
  boot_minute = boot_minute + 60
end
boot_minute_str = boot_minute.to_s()

bash "test_who" do
  cwd "/tmp"
  code <<-EOF
    echo #{boot_minute_str} >> testeeee
  EOF
end

# who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'
cron "monitor_hour_and_runlist" do
  action :create
  hour "*"
  minute boot_minute_str
  command "chef-client"
end

#UUID = /\A([0-9a-fA-F]{32}|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})\z/

ruby_block "create_tmp_del_file" do
  block do
    the_list = node.run_list
    del = true
        
    the_list.each { |rec|
      rec_str = rec.to_s()
      if rec_str['/\A([0-9a-fA-F]{32}|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})\z/']
        del = false
      end
    }

    if del
      File.open("/tmp/harakiri", "w") {}
    end
  end
end

http_request "http_delete" do
  action :delete
  url "#node{['CHOReOSData']['nodeData']['deploymentManagerURL']}/nodes/#node{['CHOReOSData']['nodeData']['nodeID']}"
  only_if do
    File.exists?("/tmp/harakiri") and b_minute == current_minute
  end
end
