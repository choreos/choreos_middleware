#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################

include_recipe "tomcat"

remote_file "war_file" do
  source "#{node['service']['$NAME']['URL']}"
  path "#{node['tomcat']['webapp_dir']}/service$NAMEDeploy.war"
  mode "0755"
  action :create
end
