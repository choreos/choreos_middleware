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

ruby_block "remove-activate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e" do
  block do
    node.run_list.remove("recipe[activate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e]")
  end
  only_if { node.run_list.include?("recipe[activate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e]") }
end


ruby_block "remove-deactivate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e" do
  block do
    node.run_list.remove("recipe[deactivate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service45fcdbfe-3e29-4bac-81f7-af9968790f4e]") }
  notifies :stop, "service[service_45fcdbfe-3e29-4bac-81f7-af9968790f4e_jar]", :immediately
end
