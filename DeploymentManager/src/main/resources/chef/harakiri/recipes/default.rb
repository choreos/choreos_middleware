#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

http_request "http_delete" do
  action :delete
  url "#node{['CHOReOSData']['nodeData']['deploymentManagerURL']}/nodes/#node{['CHOReOSData']['nodeData']['nodeID']}"
end
