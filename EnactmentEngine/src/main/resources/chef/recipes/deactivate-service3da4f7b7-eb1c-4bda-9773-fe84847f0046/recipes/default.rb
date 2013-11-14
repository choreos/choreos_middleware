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

ruby_block "remove-service3da4f7b7-eb1c-4bda-9773-fe84847f0046" do
  block do
  	node.run_list.remove("recipe[service3da4f7b7-eb1c-4bda-9773-fe84847f0046::jar]")
  end
  only_if { node.run_list.include?("recipe[service3da4f7b7-eb1c-4bda-9773-fe84847f0046::jar]") }
end

ruby_block "remove-deactivate-service3da4f7b7-eb1c-4bda-9773-fe84847f0046" do
  block do
    node.run_list.remove("recipe[deactivate-service3da4f7b7-eb1c-4bda-9773-fe84847f0046]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3da4f7b7-eb1c-4bda-9773-fe84847f0046]") }
  notifies :stop, "service[service_3da4f7b7-eb1c-4bda-9773-fe84847f0046_jar]", :immediately
end
