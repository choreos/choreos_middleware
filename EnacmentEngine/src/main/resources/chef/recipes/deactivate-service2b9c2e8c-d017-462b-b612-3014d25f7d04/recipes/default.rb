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

ruby_block "remove-activate-service2b9c2e8c-d017-462b-b612-3014d25f7d04" do
  block do
    node.run_list.remove("recipe[activate-service2b9c2e8c-d017-462b-b612-3014d25f7d04]")
  end
  only_if { node.run_list.include?("recipe[activate-service2b9c2e8c-d017-462b-b612-3014d25f7d04]") }
end


ruby_block "remove-deactivate-service2b9c2e8c-d017-462b-b612-3014d25f7d04" do
  block do
    node.run_list.remove("recipe[deactivate-service2b9c2e8c-d017-462b-b612-3014d25f7d04]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2b9c2e8c-d017-462b-b612-3014d25f7d04]") }
  notifies :stop, "service[service_2b9c2e8c-d017-462b-b612-3014d25f7d04_jar]", :immediately
end
