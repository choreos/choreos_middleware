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

ruby_block "remove-activate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134" do
  block do
    node.run_list.remove("recipe[activate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134]")
  end
  only_if { node.run_list.include?("recipe[activate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134]") }
end


ruby_block "remove-deactivate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134" do
  block do
    node.run_list.remove("recipe[deactivate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedd9d69b8-234f-4a78-ac3a-1bbb81e4a134]") }
  notifies :stop, "service[service_dd9d69b8-234f-4a78-ac3a-1bbb81e4a134_jar]", :immediately
end
