directory "/etc/ganglia-webfrontend"

case node[:platform]
when "ubuntu", "debian"
  package "ganglia-webfrontend"

  link "/etc/apache2/sites-enabled/ganglia" do
    to "/etc/ganglia-webfrontend/apache.conf"
    notifies :restart, "service[apache2]"
  end

  service "apache2" do
    supports :status => true, :restart => true, :reload => true
    action [ :enable, :start ]
  end
end
