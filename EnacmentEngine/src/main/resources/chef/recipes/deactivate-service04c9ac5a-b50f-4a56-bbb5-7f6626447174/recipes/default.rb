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

ruby_block "remove-activate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174" do
  block do
    node.run_list.remove("recipe[activate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174]")
  end
  only_if { node.run_list.include?("recipe[activate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174]") }
end


ruby_block "remove-deactivate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174" do
  block do
    node.run_list.remove("recipe[deactivate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service04c9ac5a-b50f-4a56-bbb5-7f6626447174]") }
  notifies :stop, "service[service_04c9ac5a-b50f-4a56-bbb5-7f6626447174_jar]", :immediately
end
