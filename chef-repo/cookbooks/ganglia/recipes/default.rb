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

execute "apt-get-update" do
    command "apt-get --allow-unauthenticated update"
    action :nothing
end

cookbook_file "/etc/apt/sources.list.d/ganglia.list" do
      source "ganglia.list"
      owner "root"
      group "root"
      mode 0644
      notifies :run, "execute[apt-get-update]", :immediately
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
    iplist = search(:node, "*:*").map {|node| node.ipaddress}
	if iplist.empty? 
		# This should not happen, at least this node should be found
    	iplist = ["143.107.45.126"] # Must be set by deployment manager
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
