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

ruby_block "remove-service8eadd4dc-90df-4631-844e-8248407c418d" do
  block do
  	node.run_list.remove("recipe[service8eadd4dc-90df-4631-844e-8248407c418d::jar]")
  end
  only_if { node.run_list.include?("recipe[service8eadd4dc-90df-4631-844e-8248407c418d::jar]") }
end

ruby_block "remove-deactivate-service8eadd4dc-90df-4631-844e-8248407c418d" do
  block do
    node.run_list.remove("recipe[deactivate-service8eadd4dc-90df-4631-844e-8248407c418d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8eadd4dc-90df-4631-844e-8248407c418d]") }
  notifies :stop, "service[service_8eadd4dc-90df-4631-844e-8248407c418d_jar]", :immediately
end
