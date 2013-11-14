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

ruby_block "remove-activate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4" do
  block do
    node.run_list.remove("recipe[activate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4]")
  end
  only_if { node.run_list.include?("recipe[activate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4]") }
end


ruby_block "remove-deactivate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4" do
  block do
    node.run_list.remove("recipe[deactivate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4cb77c9b-abfb-4b18-8027-3a1fd3096ea4]") }
  notifies :stop, "service[service_4cb77c9b-abfb-4b18-8027-3a1fd3096ea4_jar]", :immediately
end
