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

remote_file "#{node['service']['$NAME']['webappsPath']}/service$NAMEdeploy.war" do
  source "#{node['service']['$NAME']['URL']}"
    action :create
end

#template "#{node['service']['$NAME']['bashscript']}" do
#  source "deploy-service.sh.erb"
#  mode "0777"
#  notifies :run, resource("deploy WAR")
#end

#execute "deploy WAR" do
#  command "/bin/bash < #{node['service']['$NAME']['bashscript']}"
#  action :run
#  subscribes :create, resources("template[#{node['service']['$NAME']['bashscript']}]"), :immediately
#end

# TODO: Handle wget EXIT STATUS
#       Wget may return one of several error codes if it encounters problems.
#       0   No problems occurred.
#       1   Generic error code.
#       2   Parse error---for instance, when parsing command-line options, the .wgetrc or .netrc...
#       3   File I/O error.
#       4   Network failure.
#       5   SSL verification failure.
#       6   Username/password authentication failure.
#       7   Protocol errors.
#       8   Server issued an error response.

# temporary command to list
