#
# Cookbook Name:: ganglia
# Recipe:: default
#
# Copyright 2011, Heavy Water Software Inc.
# Modified for CHOReOS by <lago@ime.usp.br> , Sept 2012
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

apt_repository "valinhos" do
  uri "http://valinhos.ime.usp.br:54080/choreos/ubuntu/amd64/ ./"
  action :add
end

execute "apt-get-update" do
    command "apt-get --allow-unauthenticated update"
    action :nothing
end

service "ganglia-monitor" do
  pattern "gmond"
  supports :restart => true
  action :nothing
end

package "ganglia-monitor" do
	notifies :create, "template[/etc/ganglia/gmond.conf]"
    options "--allow-unauthenticated"
end

user "ganglia"
directory "/etc/ganglia"

case node[:ganglia][:unicast]
when true
  template "/etc/ganglia/gmond.conf" do
    source "gmond_unicast.conf.erb"
    # iplist = search(:node, "*:*").map {|node| node.ipaddress}
    iplist = node[:ganglia][:server_addresses] || search(:node, "role:#{node['ganglia']['server_role']} AND chef_environment:#{node.chef_environment}").map {|node| node.ipaddress}
	if iplist.empty? 
		# This should not happen, at least this node should be found
    	iplist = ["127.0.0.1"]
    end
    variables( :cluster_name => node[:ganglia][:cluster_name],
               :iplist => iplist,
               :owner => node[:ganglia][:owner],
               :latlong => node[:ganglia][:latlong],
               :url => node[:ganglia][:url],
               :location => node[:ganglia][:location]
               )
    notifies :restart, "service[ganglia-monitor]"
  end
when false
  template "/etc/ganglia/gmond.conf" do
    source "gmond.conf.erb"
    variables(  :cluster_name => node[:ganglia][:cluster_name],
    			:owner => node[:ganglia][:owner],
				:latlong => node[:ganglia][:latlong],
    			:url => node[:ganglia][:url],
				:location => node[:ganglia][:location]
    			)
    notifies :restart, "service[ganglia-monitor]"
  end
end

service "ganglia-monitor" do
  action [ :enable, :start ]
end
