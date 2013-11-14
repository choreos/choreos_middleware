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

ruby_block "remove-activate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114" do
  block do
    node.run_list.remove("recipe[activate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114]")
  end
  only_if { node.run_list.include?("recipe[activate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114]") }
end


ruby_block "remove-deactivate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114" do
  block do
    node.run_list.remove("recipe[deactivate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9848cfb1-56f8-4b52-9f92-7dbf7bec2114]") }
  notifies :stop, "service[service_9848cfb1-56f8-4b52-9f92-7dbf7bec2114_jar]", :immediately
end
