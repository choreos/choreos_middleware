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

ruby_block "remove-activate-service8e84e0b4-d69c-4422-851f-ed8c028a334c" do
  block do
    node.run_list.remove("recipe[activate-service8e84e0b4-d69c-4422-851f-ed8c028a334c]")
  end
  only_if { node.run_list.include?("recipe[activate-service8e84e0b4-d69c-4422-851f-ed8c028a334c]") }
end


ruby_block "remove-deactivate-service8e84e0b4-d69c-4422-851f-ed8c028a334c" do
  block do
    node.run_list.remove("recipe[deactivate-service8e84e0b4-d69c-4422-851f-ed8c028a334c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8e84e0b4-d69c-4422-851f-ed8c028a334c]") }
  notifies :stop, "service[service_8e84e0b4-d69c-4422-851f-ed8c028a334c_jar]", :immediately
end
