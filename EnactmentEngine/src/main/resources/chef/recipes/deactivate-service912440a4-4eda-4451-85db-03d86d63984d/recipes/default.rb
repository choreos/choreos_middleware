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

ruby_block "remove-activate-service912440a4-4eda-4451-85db-03d86d63984d" do
  block do
    node.run_list.remove("recipe[activate-service912440a4-4eda-4451-85db-03d86d63984d]")
  end
  only_if { node.run_list.include?("recipe[activate-service912440a4-4eda-4451-85db-03d86d63984d]") }
end


ruby_block "remove-deactivate-service912440a4-4eda-4451-85db-03d86d63984d" do
  block do
    node.run_list.remove("recipe[deactivate-service912440a4-4eda-4451-85db-03d86d63984d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service912440a4-4eda-4451-85db-03d86d63984d]") }
  notifies :stop, "service[service_912440a4-4eda-4451-85db-03d86d63984d_jar]", :immediately
end
