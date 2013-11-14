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

ruby_block "remove-activate-service7563c81c-55bf-4531-91de-1872166c2c3d" do
  block do
    node.run_list.remove("recipe[activate-service7563c81c-55bf-4531-91de-1872166c2c3d]")
  end
  only_if { node.run_list.include?("recipe[activate-service7563c81c-55bf-4531-91de-1872166c2c3d]") }
end


ruby_block "remove-deactivate-service7563c81c-55bf-4531-91de-1872166c2c3d" do
  block do
    node.run_list.remove("recipe[deactivate-service7563c81c-55bf-4531-91de-1872166c2c3d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7563c81c-55bf-4531-91de-1872166c2c3d]") }
  notifies :stop, "service[service_7563c81c-55bf-4531-91de-1872166c2c3d_jar]", :immediately
end
