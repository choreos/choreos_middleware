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

ruby_block "remove-activate-servicedf2b366b-270f-46d8-b867-b8853ea14407" do
  block do
    node.run_list.remove("recipe[activate-servicedf2b366b-270f-46d8-b867-b8853ea14407]")
  end
  only_if { node.run_list.include?("recipe[activate-servicedf2b366b-270f-46d8-b867-b8853ea14407]") }
end


ruby_block "remove-deactivate-servicedf2b366b-270f-46d8-b867-b8853ea14407" do
  block do
    node.run_list.remove("recipe[deactivate-servicedf2b366b-270f-46d8-b867-b8853ea14407]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedf2b366b-270f-46d8-b867-b8853ea14407]") }
  notifies :stop, "service[service_df2b366b-270f-46d8-b867-b8853ea14407_jar]", :immediately
end
