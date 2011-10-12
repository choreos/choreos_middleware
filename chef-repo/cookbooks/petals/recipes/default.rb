#
# Cookbook Name:: petals
# Recipe:: default
#
# Copyright 2011, USP
#
# LGPL 2.0 or, at your option, any later version
#
::Chef::Resource::Package.send(:include, Petals::Helpers)

ZIP_FILE = "dsb-distribution-1.0-SNAPSHOT.zip"
PETALS_URL = "http://maven.petalslink.com/public-snapshot/org/petalslink/dsb/dsb-distribution/1.0-SNAPSHOT/#{ZIP_FILE}"
PETALS_HOME = "#{node['petals']['install_dir']}/#{ZIP_FILE.gsub('.zip', '')}"

def petals_file(component_url, dir = "#{node['petals']['install_dir']}")
  file_name = component_url.split("/").last

  #download zip file
  remote_file "#{dir}/#{file_name}" do
    source component_url
    action :nothing
  end

  #install only if it has changed
  http_request "HEAD #{component_url}" do
    message ""
    url component_url
    action :head
    if File.exists?("#{dir}/#{file_name}")
      headers "If-Modified-Since" => File.mtime("#{dir}/#{file_name}").httpdate
    end
    notifies :create, resources(:remote_file => "#{dir}/#{file_name}"), :immediately
  end
end

include_recipe "java"

link "/bin/java" do
  to "/usr/bin/java"
end

include_recipe "mysql::server"


#download petals zip file
petals_file(PETALS_URL)

package "unzip" do
  action :install
end


#unzip petals
execute "unzip" do
  command "unzip #{node['petals']['install_dir']}/#{ZIP_FILE} -d #{node['petals']['install_dir']}"
  creates PETALS_HOME
  action :run
end

directory "#{PETALS_HOME}/downloads" do
  owner "root"
  group "root"
  mode "0755"
  action :create
end

#install components
PETALS_COMPONENTS = %w{
  http://download.forge.objectweb.org/petals/petals-se-rmi-1.1.1.zip
  http://maven.ow2.org/maven2/org/ow2/petals/petals-bc-ejb/1.3/petals-bc-ejb-1.3.zip
  http://maven.ow2.org/maven2/org/ow2/petals/petals-bc-soap/4.0.4/petals-bc-soap-4.0.4.zip
  http://maven.ow2.org/maven2/org/ow2/petals/petals-se-bpel/1.0.6/petals-se-bpel-1.0.6.zip
  http://download.forge.objectweb.org/petals/petals-sl-easybeans-1.0.2.zip
}

PETALS_COMPONENTS.each do |component_url|
  petals_file(component_url, "#{PETALS_HOME}/downloads/")
end

template "#{PETALS_HOME}/conf/server.properties" do
  source "server.properties.erb"
  owner "root"
  group "root"
  mode "0644"
end

@@master = node

search(:node, 'role:petals') do |n|
  if n['petals']['container_type'] == "master"
    @@master = n
    if n.name != node.name
      node.set['petals']['container_type'] = "slave"
    end
  end
end

if @@master == node
  node.set['petals']['container_type'] = "master"
end

template "#{PETALS_HOME}/conf/topology.xml" do
  source "topology.xml.erb"
  owner "root"
  group "root"
  mode "0644"
  variables({:master => @@master})
end

template "#{PETALS_HOME}/conf/dsb.cfg" do
  source "dsb.cfg.erb"
  owner "root"
  group "root"
  mode "0644"
end

template "#{PETALS_HOME}/conf/launcher.cfg" do
  source "launcher.cfg.erb"
  owner "root"
  group "root"
  mode "0644"
end



mysql_database "creates petals database" do
  host "localhost"
  username "root"
  password node[:mysql][:server_root_password]
  database "petals"
  action :create_db
end


ENV['JAVA_HOME'] = '/usr/lib/jvm/java-6-sun'

template "/etc/profile.d/java.sh" do
  source "etc/profile.d/java.sh.erb"
  variables( :JAVA_HOME => ENV['JAVA_HOME'] )
  mode 0644
end

template "/etc/init.d/petals" do
  source "etc/init.d/petals"
  mode 0755
end

service "petals" do
  supports :start => true, :stop => true
  action [ :enable, :start ]
end

#things needed to run services
package "ant" do
  action :install
end

