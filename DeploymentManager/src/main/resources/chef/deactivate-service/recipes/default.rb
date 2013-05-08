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

ruby_block "remove-activate-service$NAME" do
  block do
    node.run_list.remove("recipe[activate-service$NAME]")
  end
  only_if { node.run_list.include?("recipe[activate-service$NAME]") }
end


ruby_block "remove-deactivate-service$NAME" do
  block do
    node.run_list.remove("recipe[deactivate-service$NAME]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service$NAME]") }
  notifies :stop, "service[service_$NAME_jar]", :immediately
end
