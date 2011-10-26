#
# Cookbook Name:: shipper
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
include_recipe 'petals'

DOWNLOAD_SERVER = 'http://valinhos.ime.usp.br:54080/demo/shipper'
PETALS_DIR = node['petals']['dir']

def download(filename)
  diskFile = "/tmp/#{filename}"
  webFile = "#{DOWNLOAD_SERVER}/#{filename}"

  remote_file diskFile do
    source webFile
    action :create_if_missing
  end
end

# Install components
components = %w{
  sa-BPEL-Shipper1-provide.zip
  sa-SOAP-Shipper1WS-provide.zip
  sa-SOAP-Shipper1-consume.zip
}

components.each do |filename|
  download filename
  execute 'install component' do
    command "cp /tmp/#{filename} #{PETALS_DIR}/install"
    creates "#{PETALS_DIR}/installed/#{filename}"
    action :run
  end
end

# Install webapp
filename = 'runShipperWS.jar'
download filename
execute 'install webapp' do
  command "cp /tmp/#{filename} #{PETALS_DIR}/webapps"
  creates "#{PETALS_DIR}/webapps/#{filename}"
  action :run
end

# Runs webapp
service "shipper_ws" do
  supports :start => true
  start_command "java -jar #{PETALS_DIR}/webapps/runShipperWS.jar 1234"
  action [ :start ]
end
