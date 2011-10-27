#
# Cookbook Name:: customer
# Recipe:: default
#
# Copyright 2011, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#
include_recipe 'petals'

DOWNLOAD_SERVER = 'http://valinhos.ime.usp.br:54080/demo/'
PETALS_DIR = node['petals']['dir']

def download(filename)
  basename = filename[filename.rindex('/')+1..-1]
  diskFile = "/tmp/#{basename}"
  webFile = "#{DOWNLOAD_SERVER}/#{filename}"

  remote_file diskFile do
    source webFile
    action :create_if_missing
  end
end

# Install components
components = %w{
  shipper/sa-SOAP-Shipper1-consume.zip
  smregistry/sa-SOAP-SMRegistry-consume.zip
  supermarket1/sa-SOAP-SM1-consume.zip
  supermarket2/sa-SOAP-SM2-consume.zip
  supermarket3/sa-SOAP-SM3-consume.zip
}

components.each do |filename|
  download filename
  basename = filename[filename.rindex('/')+1..-1]
  execute 'install component' do
    command "cp /tmp/#{basename} #{PETALS_DIR}/install"
    creates "#{PETALS_DIR}/installed/#{basename}"
    action :run
  end
end
