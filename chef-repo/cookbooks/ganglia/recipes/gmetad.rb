case node[:platform]
when "ubuntu", "debian"
  package "gmetad"
end

directory "/var/lib/ganglia/rrds" do
  owner "nobody"
  recursive true
end

case node[:ganglia][:unicast]
when true
  template "/etc/ganglia/gmetad.conf" do
    source "gmetad.conf.erb"
    variables( :hosts => "localhost",
               :cluster_name => node[:ganglia][:cluster_name])
    notifies :restart, "service[gmetad]"
  end
  if node[:recipes].include? "iptables"
    include_recipe "ganglia::iptables"
  end
when false
  ips = search(:node, "*:*").map {|node| node.ipaddress}
  if ips.empty?
  	# This should not happen, at least this node should be listed
  	ips = "localhost"
  end
  template "/etc/ganglia/gmetad.conf" do
    source "gmetad.conf.erb"
    variables( :hosts => ips.join(" "),
               :cluster_name => node[:ganglia][:cluster_name])
    notifies :restart, "service[gmetad]"
  end
end

service "gmetad" do
  supports :restart => true
  action [ :enable, :start ]
end
