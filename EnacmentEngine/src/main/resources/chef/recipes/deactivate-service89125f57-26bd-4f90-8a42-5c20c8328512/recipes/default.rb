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

ruby_block "remove-activate-service89125f57-26bd-4f90-8a42-5c20c8328512" do
  block do
    node.run_list.remove("recipe[activate-service89125f57-26bd-4f90-8a42-5c20c8328512]")
  end
  only_if { node.run_list.include?("recipe[activate-service89125f57-26bd-4f90-8a42-5c20c8328512]") }
end


ruby_block "remove-deactivate-service89125f57-26bd-4f90-8a42-5c20c8328512" do
  block do
    node.run_list.remove("recipe[deactivate-service89125f57-26bd-4f90-8a42-5c20c8328512]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service89125f57-26bd-4f90-8a42-5c20c8328512]") }
  notifies :stop, "service[service_89125f57-26bd-4f90-8a42-5c20c8328512_jar]", :immediately
end
