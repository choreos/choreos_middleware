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

ruby_block "remove-servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7" do
  block do
  	node.run_list.remove("recipe[servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7::jar]") }
end

ruby_block "remove-deactivate-servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7" do
  block do
    node.run_list.remove("recipe[deactivate-servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea5f8ba3b-f643-4617-a60e-7413cfe7bab7]") }
  notifies :stop, "service[service_a5f8ba3b-f643-4617-a60e-7413cfe7bab7_jar]", :immediately
end
