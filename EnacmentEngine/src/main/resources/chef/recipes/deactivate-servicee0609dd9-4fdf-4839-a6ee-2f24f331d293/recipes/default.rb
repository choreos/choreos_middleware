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

ruby_block "remove-activate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293" do
  block do
    node.run_list.remove("recipe[activate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293]")
  end
  only_if { node.run_list.include?("recipe[activate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293]") }
end


ruby_block "remove-deactivate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293" do
  block do
    node.run_list.remove("recipe[deactivate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee0609dd9-4fdf-4839-a6ee-2f24f331d293]") }
  notifies :stop, "service[service_e0609dd9-4fdf-4839-a6ee-2f24f331d293_jar]", :immediately
end
