#
# Cookbook Name:: petals
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
include_recipe "java"
include_recipe "mysql"
include_recipe "mysql::server"

PETALS_URL = "http://maven.ow2.org/maven2/org/ow2/petals/petals-platform/3.1.1/petals-platform-3.1.1.zip"

#download petals zip file
remote_file "/var/petals-platform-3.1.1.zip" do
  source PETALS_URL
  action :nothing
end

#only if it has changed
http_request "HEAD #{PETALS_URL}" do
  message ""
  url PETALS_URL
  action :head
  if File.exists?("/var/petals-platform-3.1.1.zip")
    headers "If-Modified-Since" => File.mtime("/var/petals-platform-3.1.1.zip").httpdate
  end
  notifies :create, resources(:remote_file => "/var/petals-platform-3.1.1.zip"), :immediately
end

#unzip petals
execute "unzip" do
  command "unzip /var/petals-platform-3.1.1.zip -d /var"
  creates "/var/petals-platform-3.1.1"
  action :run
end
