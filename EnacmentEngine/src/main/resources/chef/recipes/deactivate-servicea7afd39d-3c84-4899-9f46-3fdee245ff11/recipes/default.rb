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

ruby_block "remove-servicea7afd39d-3c84-4899-9f46-3fdee245ff11" do
  block do
  	node.run_list.remove("recipe[servicea7afd39d-3c84-4899-9f46-3fdee245ff11::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea7afd39d-3c84-4899-9f46-3fdee245ff11::jar]") }
end

ruby_block "remove-deactivate-servicea7afd39d-3c84-4899-9f46-3fdee245ff11" do
  block do
    node.run_list.remove("recipe[deactivate-servicea7afd39d-3c84-4899-9f46-3fdee245ff11]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea7afd39d-3c84-4899-9f46-3fdee245ff11]") }
  notifies :stop, "service[service_a7afd39d-3c84-4899-9f46-3fdee245ff11_jar]", :immediately
end
