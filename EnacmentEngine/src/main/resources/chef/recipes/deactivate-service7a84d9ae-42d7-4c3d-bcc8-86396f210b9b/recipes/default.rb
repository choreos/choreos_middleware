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

ruby_block "remove-activate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b" do
  block do
    node.run_list.remove("recipe[activate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b]")
  end
  only_if { node.run_list.include?("recipe[activate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b]") }
end


ruby_block "remove-deactivate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b" do
  block do
    node.run_list.remove("recipe[deactivate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7a84d9ae-42d7-4c3d-bcc8-86396f210b9b]") }
  notifies :stop, "service[service_7a84d9ae-42d7-4c3d-bcc8-86396f210b9b_jar]", :immediately
end
