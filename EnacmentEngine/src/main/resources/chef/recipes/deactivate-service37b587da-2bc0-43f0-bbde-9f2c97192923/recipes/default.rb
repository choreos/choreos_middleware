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

ruby_block "remove-activate-service37b587da-2bc0-43f0-bbde-9f2c97192923" do
  block do
    node.run_list.remove("recipe[activate-service37b587da-2bc0-43f0-bbde-9f2c97192923]")
  end
  only_if { node.run_list.include?("recipe[activate-service37b587da-2bc0-43f0-bbde-9f2c97192923]") }
end


ruby_block "remove-deactivate-service37b587da-2bc0-43f0-bbde-9f2c97192923" do
  block do
    node.run_list.remove("recipe[deactivate-service37b587da-2bc0-43f0-bbde-9f2c97192923]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service37b587da-2bc0-43f0-bbde-9f2c97192923]") }
  notifies :stop, "service[service_37b587da-2bc0-43f0-bbde-9f2c97192923_jar]", :immediately
end
