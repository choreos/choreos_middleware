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

ruby_block "remove-service34742ae4-9f73-4907-bbba-0f6f56c13ba3" do
  block do
  	node.run_list.remove("recipe[service34742ae4-9f73-4907-bbba-0f6f56c13ba3::jar]")
  end
  only_if { node.run_list.include?("recipe[service34742ae4-9f73-4907-bbba-0f6f56c13ba3::jar]") }
end

ruby_block "remove-deactivate-service34742ae4-9f73-4907-bbba-0f6f56c13ba3" do
  block do
    node.run_list.remove("recipe[deactivate-service34742ae4-9f73-4907-bbba-0f6f56c13ba3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service34742ae4-9f73-4907-bbba-0f6f56c13ba3]") }
  notifies :stop, "service[service_34742ae4-9f73-4907-bbba-0f6f56c13ba3_jar]", :immediately
end
