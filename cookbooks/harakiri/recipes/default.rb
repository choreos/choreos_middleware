#
# Cookbook Name:: harakiri
# Recipe:: default
#
# Copyright 2013, IME USP
# Thiago Furtado
#

boot_minute_str = `who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'`.strip
boot_minute = boot_minute_str.to_i()

harakiri_minute = boot_minute - 3
if harakiri_minute < 0
  harakiri_minute = harakiri_minute + 60
end

current_minute_str = `date +%M`.strip
current_minute = current_minute_str.to_i()

b_m = boot_minute + 100
h_m = b_m - 3
c_m = current_minute + 100

# who -b | sed -e 's/^.*system boot .//g' -e 's/[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9].[0-9][0-9]://g'
cron "run_chef_solo" do
  action :create
  hour "*"
  minute harakiri_minute
  user "ubuntu"
  command "sudo chef-solo -c #{ENV['HOME']}/chef-solo/solo.rb >> /tmp/chef-solo-harakiri.log 2>&1"
end

# no services but easyesb
if !(File.read("#{ENV['HOME']}/chef-solo/node.json") =~ /[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/) and (File.read("#{ENV['HOME']}/chef-solo/node.json") =~ /easyesb/) and c_m >= h_m and c_m < b_m then
        file "/tmp/trash" do
                action :nothing
        end
end

# no services, no easyeasb : should request delete
if !(File.read("#{ENV['HOME']}/chef-solo/node.json") =~ /[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}/) and !(File.read("#{ENV['HOME']}/chef-solo/node.json") =~ /easyesb/) and c_m >= h_m and c_m < b_m then

	bash "debug" do
		code "echo boot #{b_m} current #{c_m} harakiri #{h_m} current_minute #{current_minute} current_minute_str #{current_minute_str} aa> /tmp/eitanois"
	end

       	http_request "http_delete" do
       	        action :delete
       	        message ""
       	        url "#{node['CHOReOSData']['nodeData']['deploymentManagerURL']}/nodes/#{node['CHOReOSData']['nodeData']['nodeID']}"
       	end

       	file "/tmp/harakiri_testing" do
       	        action :create
       	end

end

