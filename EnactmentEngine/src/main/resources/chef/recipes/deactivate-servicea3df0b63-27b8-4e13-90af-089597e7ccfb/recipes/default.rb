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

ruby_block "remove-activate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb" do
  block do
    node.run_list.remove("recipe[activate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb]")
  end
  only_if { node.run_list.include?("recipe[activate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb]") }
end


ruby_block "remove-deactivate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb" do
  block do
    node.run_list.remove("recipe[deactivate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea3df0b63-27b8-4e13-90af-089597e7ccfb]") }
  notifies :stop, "service[service_a3df0b63-27b8-4e13-90af-089597e7ccfb_jar]", :immediately
end
