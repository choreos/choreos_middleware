#
# Cookbook Name:: customer
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
include_recipe 'petals'

DOWNLOAD_SERVER = 'http://valinhos.ime.usp.br:54080/demo/customer'
PETALS_DIR = node['petals']['dir']
DIR2SAVE = PETALS_DIR[/\/opt\/.*\//][0..-2] # first dir below /opt

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
  sa-BPEL-Customer-provide.zip
  sa-SOAP-Customer-consume.zip
  sa-SOAP-CustomerWS-provide.zip
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
filename = 'runCustomerWS.jar'
download filename
execute 'save webapp' do
  command "cp /tmp/#{filename} #{DIR2SAVE}/"
  creates "#{DIR2SAVE}/#{filename}"
  action :run
end

# Runs webapp
service "customer_ws" do
  supports :start => true
  start_command "java -jar #{DIR2SAVE}/runCustomerWS.jar 1234 &"
  action [ :start ]
end
