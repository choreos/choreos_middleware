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

ruby_block "remove-activate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6" do
  block do
    node.run_list.remove("recipe[activate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6]")
  end
  only_if { node.run_list.include?("recipe[activate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6]") }
end


ruby_block "remove-deactivate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6" do
  block do
    node.run_list.remove("recipe[deactivate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2a0d49ad-9400-4904-907a-dfd1078d1fb6]") }
  notifies :stop, "service[service_2a0d49ad-9400-4904-907a-dfd1078d1fb6_jar]", :immediately
end
