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

ruby_block "remove-serviceb394f47e-dce4-4a76-a78e-91a5830d288d" do
  block do
  	node.run_list.remove("recipe[serviceb394f47e-dce4-4a76-a78e-91a5830d288d::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb394f47e-dce4-4a76-a78e-91a5830d288d::jar]") }
end

ruby_block "remove-deactivate-serviceb394f47e-dce4-4a76-a78e-91a5830d288d" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb394f47e-dce4-4a76-a78e-91a5830d288d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb394f47e-dce4-4a76-a78e-91a5830d288d]") }
  notifies :stop, "service[service_b394f47e-dce4-4a76-a78e-91a5830d288d_jar]", :immediately
end
