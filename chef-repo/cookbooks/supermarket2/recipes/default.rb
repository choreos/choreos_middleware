#
# Cookbook Name:: supermarket2
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

::Chef::Resource::Package.send(:include, Petals::Helpers)

def petals_file(component_url, dir)
  file_name = component_url.split("/").last

  #download zip file
  remote_file "#{node['petals']['install_dir']}/#{dir}#{file_name}" do
    source component_url
    action :nothing
  end

  #install only if it has changed
  http_request "HEAD #{component_url}" do
    message ""
    url component_url
    action :head
    if File.exists?("#{node['petals']['install_dir']}/#{dir}#{file_name}")
      headers "If-Modified-Since" => File.mtime("#{node['petals']['install_dir']}/#{dir}#{file_name}").httpdate
    end
    notifies :create, resources(:remote_file => "#{node['petals']['install_dir']}/#{dir}#{file_name}"), :immediately
  end
end

include_recipe "petals"

#install components
SM2_COMPONENTS = %w{
  http://valinhos.ime.usp.br:54080/demo/supermarket2/sa-BPEL-SM2-provide.zip
  http://valinhos.ime.usp.br:54080/demo/supermarket2/sa-SOAP-SM2-provide.zip
  http://valinhos.ime.usp.br:54080/demo/supermarket2/sa-SOAP-SM2-consume.zip
}

SM2_COMPONENTS.each do |component_url|
  petals_file(component_url, "dsb-distribution-1.0-SNAPSHOT/install/")
end

JAR_FILE="http://valinhos.ime.usp.br:54080/demo/supermarket2/runSM2.jar"

petals_file(JAR_FILE, "dsb-distribution-1.0-SNAPSHOT/webapps/")

#running service
service "supermarket2_ws" do
  supports :start => true
  start_command = "java -jar #{node['petals']['install_dir']}/webapps/runSM2.jar 4321"
  action [ :enable, :start ]
end
